package com.spring.marketplace.controller;

import com.spring.marketplace.dto.ProductDto;
import com.spring.marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable UUID id) {
        return (ProductDto) productService.getProduct(id);
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return "Product with id: " + id + " was deleted";
    }

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
}
