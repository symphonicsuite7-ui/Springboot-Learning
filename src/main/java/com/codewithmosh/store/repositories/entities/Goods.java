package com.codewithmosh.store.repositories.entities;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Goods {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
}
