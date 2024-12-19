package com.spring.marketplace.utils.converter;


import com.spring.marketplace.model.entity.Product;
import com.spring.marketplace.utils.factory.AbstractFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToEntityConverter implements Converter<Object, Product> {

    private final AbstractFactory abstractFactory;

    @Override
    public Product convert(Object source) {

        return abstractFactory.createProduct(source);
    }

    @Autowired
    public DtoToEntityConverter(@Qualifier("getDtoFactory") AbstractFactory abstractFactory) {

        this.abstractFactory = abstractFactory;
    }
}
