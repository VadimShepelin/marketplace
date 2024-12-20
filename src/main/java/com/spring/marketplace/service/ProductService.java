package com.spring.marketplace.service;



import java.util.List;

public interface ProductService<Uuid, ProductDto> {

    List<ProductDto> getProducts();

    ProductDto getProduct(Uuid id);

    void saveProduct(ProductDto product);

    void deleteProduct(Uuid id);
}
