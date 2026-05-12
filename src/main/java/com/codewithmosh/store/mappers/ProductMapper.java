package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.ProductsDto;
import com.codewithmosh.store.repositories.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductsDto toDto(Product product);

    Product toEntity(ProductsDto request);
}
