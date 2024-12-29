package com.spring.marketplace.controller;

import com.spring.marketplace.dto.CreateProductDto;
import com.spring.marketplace.dto.ReadProductDto;
import com.spring.marketplace.dto.UpdateProductDto;
import com.spring.marketplace.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ReadProductDto> getAllProducts(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        return productService.getAllProducts(pageNo,pageSize);
    }

    @GetMapping("/{id}")
    public ReadProductDto getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product was deleted");
    }

    @PostMapping
    public ReadProductDto createProduct(@Valid @RequestBody CreateProductDto productDto) {
       return productService.saveProduct(productDto);
    }

    @PutMapping
    public ReadProductDto updateProduct(@Valid @RequestBody UpdateProductDto productDto) {
        return productService.updateProduct(productDto);
    }

}
