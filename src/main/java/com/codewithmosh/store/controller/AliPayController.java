package com.codewithmosh.store.controller;

//import com.alipay.fastjson.JSONObject;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.codewithmosh.store.common.AliPayConfig;
import com.alipay.api.AlipayClient;
import com.codewithmosh.store.repositories.entities.Orders;
import com.codewithmosh.store.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
