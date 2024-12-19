package com.spring.marketplace.utils.factory;

import com.spring.marketplace.dto.ProductReadDto;
import com.spring.marketplace.model.entity.Product;

public abstract class AbstractFactory {

    public Product createProduct(Object object){
        return null;
    }

    public ProductReadDto createProductReadDto(Product product){
        return null;
    }

}
