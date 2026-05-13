package com.codewithmosh.store.service;

import com.codewithmosh.store.dtos.ProductsDto;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import com.codewithmosh.store.repositories.entities.Category;
import com.codewithmosh.store.repositories.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryRepository categoryRepository;

    public ProductsDto createProduct(ProductsDto request) {

        Product product = productMapper.toEntity(request);

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("分类不存在"));

        product.setCategory(category);

        productRepository.save(product);

        return productMapper.toDto(product);
    }

}
