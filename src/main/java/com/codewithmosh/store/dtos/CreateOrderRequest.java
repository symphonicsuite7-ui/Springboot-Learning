package com.codewithmosh.store.dtos;

import com.codewithmosh.store.repositories.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class CreateOrderRequest {

    private Long id;

    private String orderNo;

    private BigDecimal total;

    private Product product;
}
