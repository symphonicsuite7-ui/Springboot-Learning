package com.codewithmosh.store.service;

import com.codewithmosh.store.common.BusinessException;
import com.codewithmosh.store.dtos.CreateOrderRequest;
import com.codewithmosh.store.mappers.OrderMapper;
import com.codewithmosh.store.repositories.OrderRepository;
import com.codewithmosh.store.repositories.entities.OrderStatus;
import com.codewithmosh.store.repositories.entities.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Orders createOrder(CreateOrderRequest request) {

        Orders order = new Orders();

        // 生成订单号
        String orderNo = System.currentTimeMillis() + "";

        order.setOrderNo(orderNo);

        System.out.println(request.getGoodsName());
        order.setGoodsName(request.getGoodsName());

        order.setTotal(request.getTotal());

        order.setStatus(OrderStatus.CREATED);

        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public Orders selectByOrderNo(String orderNo) {

        return orderRepository.findByOrderNo(orderNo);
    }
    public void updateOrderStatus(String orderNo, OrderStatus status) {
        Orders order = orderRepository.findByOrderNo(orderNo);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }

}