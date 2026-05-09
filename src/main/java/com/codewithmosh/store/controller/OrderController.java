package com.codewithmosh.store.controller;

import com.codewithmosh.store.common.ApiResponse;
import com.codewithmosh.store.dtos.CreateOrderRequest;
import com.codewithmosh.store.repositories.entities.Orders;
import com.codewithmosh.store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<Orders> createOrder(
            @RequestBody CreateOrderRequest request
    ) {

        return ApiResponse.success(
                orderService.createOrder(request)
        );
    }
}