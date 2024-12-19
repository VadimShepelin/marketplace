package com.spring.marketplace.utils.converter;

import com.spring.marketplace.dto.ProductReadDto;
import com.spring.marketplace.model.entity.Product;
import com.spring.marketplace.utils.factory.AbstractFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class EntityToDtoConverter implements Converter<Product, ProductReadDto> {

    private final AbstractFactory abstractFactory;

    @Override
    public ProductReadDto convert(Product source) {
        return abstractFactory.createProductReadDto(source);
    }

    @Autowired
    public EntityToDtoConverter(@Qualifier("getProductFactory") AbstractFactory abstractFactory) {
        this.abstractFactory = abstractFactory;
    }
}
