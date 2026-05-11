package com.codewithmosh.store.service;

import com.codewithmosh.store.common.BusinessException;
import com.codewithmosh.store.dtos.CreateOrderRequest;
import com.codewithmosh.store.mappers.OrderMapper;
import com.codewithmosh.store.repositories.OrderRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import com.codewithmosh.store.repositories.entities.OrderStatus;
import com.codewithmosh.store.repositories.entities.Orders;
import com.codewithmosh.store.repositories.entities.Product;
import lombok.RequiredArgsConstructor;
import okhttp3.internal.http2.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    @Transactional
    public Orders createOrder(CreateOrderRequest request) {

        Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException(
                        404,
                        "Product not found"
                ));

        if (product.getStock() <= 0) {
            throw new BusinessException(
                    400,
                    "Not enough stock"
            );
        }
        product.setStock(product.getStock() - 1);

        productRepository.save(product);


        Orders order = new Orders();

        // 生成订单号
        String orderNo = "ORD" +
                System.currentTimeMillis() +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 6);

        order.setOrderNo(orderNo);

        System.out.println(product.getName());
        order.setGoodsName(product.getName());

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

        }}