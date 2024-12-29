package com.spring.marketplace.service;

import com.spring.marketplace.dto.CreateProductDto;
import com.spring.marketplace.dto.ReadProductDto;
import com.spring.marketplace.dto.UpdateProductDto;
import java.util.List;
import java.util.UUID;

public interface ProductService{

    List<ReadProductDto> getAllProducts(int pageNo, int pageSize);

    ReadProductDto getProductById(UUID id);

    ReadProductDto saveProduct(CreateProductDto product);

    void deleteProduct(UUID id);

    ReadProductDto updateProduct(UpdateProductDto product);
}
