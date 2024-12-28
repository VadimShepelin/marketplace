package com.spring.marketplace.service;

import com.spring.marketplace.dto.CreateProductDto;
import java.util.List;
import java.util.UUID;

public interface ProductService{

    List<CreateProductDto> getAllProducts(int pageNo, int pageSize);

    CreateProductDto getProductById(UUID id);

    CreateProductDto saveProduct(CreateProductDto product);

    void deleteProduct(UUID id);

    CreateProductDto updateProduct(CreateProductDto product);
}
