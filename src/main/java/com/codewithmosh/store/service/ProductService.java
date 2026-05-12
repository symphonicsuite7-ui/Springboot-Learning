package com.codewithmosh.store.service;

import com.codewithmosh.store.dtos.ProductsDto;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductsDto createProduct(ProductsDto request) {
        var product = productMapper.toEntity(request);
        productRepository.save(product);
        return productMapper.toDto(product);
    }
}
