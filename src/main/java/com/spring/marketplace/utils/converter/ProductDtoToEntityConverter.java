package com.spring.marketplace.utils.converter;


import com.spring.marketplace.dto.ProductDto;
import com.spring.marketplace.model.Product;
import com.spring.marketplace.utils.factory.AbstractFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoToEntityConverter implements Converter<ProductDto, Product> {

    private final AbstractFactory abstractFactory;

    @Override
    public Product convert(ProductDto source) {

        return abstractFactory.createProduct(source);
    }

    @Autowired
    public ProductDtoToEntityConverter(@Qualifier("getProductDtoFactory") AbstractFactory abstractFactory) {

        this.abstractFactory = abstractFactory;
    }
}
