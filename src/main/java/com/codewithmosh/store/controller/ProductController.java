package com.codewithmosh.store.controller;

import com.codewithmosh.store.dtos.ProductsDto;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.ProductRepository;
import com.codewithmosh.store.repositories.entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    @GetMapping ("")
    private List<ProductsDto> getAllProducts(
            @RequestParam (name = "categoryId",required = false) Byte categoryId
    ) {
        List<Product> products;
        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAllWithCategory();
        }
        return products
                .stream()
                .map(productMapper::toDto)
                .toList();
    }
    @RequestMapping("/{id}")
    private ProductsDto getProductById(@PathVariable Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }
        return productMapper.toDto(product);
    }
}
