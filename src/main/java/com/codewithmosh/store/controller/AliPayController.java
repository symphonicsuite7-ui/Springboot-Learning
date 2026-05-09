package com.codewithmosh.store.controller;

//import com.alipay.fastjson.JSONObject;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.codewithmosh.store.common.AliPayConfig;
import com.alipay.api.AlipayClient;
import com.codewithmosh.store.repositories.entities.OrderStatus;
import com.codewithmosh.store.repositories.entities.Orders;
import com.codewithmosh.store.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/alipay")
public class AliPayController {

    //支付宝沙箱网关地址

    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    private static final String FORMAT = "JSON";

    private static final String CHARSET = "UTF-8";

    private static final String SIGN_TYPE = "RSA2";

    @Resource
    private AliPayConfig aliPayConfig;

    @Resource
    private OrderService orderService;

    @RequestMapping("/pay")
    public void pay(String orderNo, HttpServletResponse httpResponse) throws  Exception{
        //查询订单信息
        Orders order = orderService.selectByOrderNo(orderNo);
        if (order == null) {
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
            return;
        }

        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);

        //2.创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();//发送请求的 Request类
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", order.getOrderNo());//我们自己生成的订单编号
        bizContent.put("total_amount", order.getTotal().toPlainString());//订单的总金额
        bizContent.put("subject", order.getGoodsName()); // 支付的名称
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY"); // 固定配置
        request.setBizContent(bizContent.toString());
        request.setReturnUrl("http://localhost:25774/orders");

        //执行请求，拿到相应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }
    @RequestMapping("/notify")
    public String notify(HttpServletRequest request) {
        try {
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }

            // 验证签名
            boolean signVerified = com.alipay.api.internal.util.AlipaySignature.rsaCheckV1(
                    params,
                    aliPayConfig.getAlipayPublicKey(),
                    CHARSET,
                    SIGN_TYPE
            );

            if (signVerified) {
                // 签名验证成功，处理业务逻辑
                String tradeStatus = params.get("trade_status");
                String orderNo = params.get("out_trade_no");

                // 判断支付结果
                if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
                    // 支付成功，更新订单状态
                    orderService.updateOrderStatus(orderNo, OrderStatus.PAID);
                    System.out.println("订单 " + orderNo + " 支付成功，状态已更新为PAID");
                } else if ("TRADE_CLOSED".equals(tradeStatus)) {
                    // 交易关闭
                    orderService.updateOrderStatus(orderNo, OrderStatus.CANCELLED);
                    System.out.println("订单 " + orderNo + " 交易关闭，状态已更新为CANCELLED");
                }

                return "success";
            } else {
                // 签名验证失败
                System.err.println("支付宝签名验证失败");
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
