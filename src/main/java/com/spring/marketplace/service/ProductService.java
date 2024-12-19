package com.spring.marketplace.service;

import com.spring.marketplace.dto.ProductReadDto;

import java.util.List;
import java.util.Optional;

public interface ProductService<Uuid, ProductResponseDto> {

    List<ProductResponseDto> getProducts();

    Optional<ProductResponseDto> getProduct(Uuid id);

    void saveProduct(ProductReadDto product);

    void deleteProduct(Uuid id);
}
