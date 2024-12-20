package com.spring.marketplace.utils.factory;

import com.spring.marketplace.dto.ProductDto;
import com.spring.marketplace.model.Product;

public abstract class AbstractFactory {

    public Product createProduct(ProductDto productDto){
        return null;
    }

    public ProductDto createProductReadDto(Product product){
        return null;
    }

}
