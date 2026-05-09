package com.codewithmosh.store.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class OrdersDto {

    private Long id;

    private String orderNo;

    private BigDecimal total;

    private String goodsName;
}
