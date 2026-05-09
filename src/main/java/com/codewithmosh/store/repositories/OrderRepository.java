package com.codewithmosh.store.repositories;

import com.codewithmosh.store.repositories.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    Orders findByOrderNo(String orderNo);

}
