package com.spring.marketplace.service;

import com.spring.marketplace.dto.ProductDto;
import java.util.List;
import java.util.UUID;

public interface ProductService{

    List<ProductDto> getAllProducts(int pageNo, int pageSize);

    ProductDto getProductById(UUID id);

    ProductDto saveProduct(ProductDto product);

    void deleteProduct(UUID id);

    ProductDto updateProduct(ProductDto product);
}
