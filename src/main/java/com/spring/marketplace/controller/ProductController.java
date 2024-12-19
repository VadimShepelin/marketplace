package com.spring.marketplace.controller;

import com.spring.marketplace.dto.ProductResponseDto;
import com.spring.marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponseDto> getAllProducts() {
        return productService.getProducts();
    }

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
}
