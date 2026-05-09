package com.codewithmosh.store.repositories.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 订单编号
    private String orderNo;

    // 商品名称

    private String goodsName;

    // 总金额
    private BigDecimal total;

    // 状态
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 创建时间
    private LocalDateTime createdAt;


}
