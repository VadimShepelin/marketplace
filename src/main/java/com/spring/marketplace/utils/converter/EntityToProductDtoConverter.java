package com.spring.marketplace.utils.converter;

import com.spring.marketplace.dto.ProductDto;
import com.spring.marketplace.model.Product;
import com.spring.marketplace.utils.factory.AbstractFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class EntityToProductDtoConverter implements Converter<Product, ProductDto> {

    private final AbstractFactory abstractFactory;

    @Override
    public ProductDto convert(Product source) {
        return abstractFactory.createProductDto(source);
    }

    @Autowired
    public EntityToProductDtoConverter(@Qualifier("getProductFactory") AbstractFactory abstractFactory) {
        this.abstractFactory = abstractFactory;
    }
}
