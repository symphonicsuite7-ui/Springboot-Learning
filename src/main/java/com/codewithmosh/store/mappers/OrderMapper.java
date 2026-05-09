package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.CreateOrderRequest;
import com.codewithmosh.store.repositories.entities.Orders;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    CreateOrderRequest toDto(Orders  order);

}
