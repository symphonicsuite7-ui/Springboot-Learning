package com.codewithmosh.store.service;

import com.codewithmosh.store.common.BusinessException;
import com.codewithmosh.store.dtos.OrdersDto;
import com.codewithmosh.store.mappers.OrderMapper;
import com.codewithmosh.store.repositories.OrderRepository;
import com.codewithmosh.store.repositories.entities.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrdersDto getOrderById(Long id) {
        var order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new BusinessException(404, "Order not found");
        }
        return orderMapper.toDto(order);
    }
    public Orders selectByOrderNo(String orderNo) {
        return orderRepository.findByOrderNo(orderNo).orElse(null);
    }
}
