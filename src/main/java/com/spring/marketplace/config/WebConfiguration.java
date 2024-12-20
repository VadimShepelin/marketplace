package com.spring.marketplace.config;

import com.spring.marketplace.utils.converter.ProductDtoToEntityConverter;
import com.spring.marketplace.utils.converter.EntityToProductDtoConverter;
import com.spring.marketplace.utils.factory.AbstractFactory;
import com.spring.marketplace.utils.factory.ProductDtoFactory;
import com.spring.marketplace.utils.factory.ProductFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ProductDtoToEntityConverter(getProductFactory()));
        registry.addConverter(new EntityToProductDtoConverter(getProductDtoFactory()));
    }

    @Bean
    public AbstractFactory getProductDtoFactory(){
        return new ProductDtoFactory();
    }

    @Bean
    public AbstractFactory getProductFactory(){
        return new ProductFactory();
    }
}
