package com.spring.marketplace.config;

import com.spring.marketplace.model.entity.Product;
import com.spring.marketplace.utils.converter.DtoToEntityConverter;
import com.spring.marketplace.utils.converter.EntityToDtoConverter;
import com.spring.marketplace.utils.factory.AbstractFactory;
import com.spring.marketplace.utils.factory.DtoFactory;
import com.spring.marketplace.utils.factory.ProductFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DtoToEntityConverter(getProductFactory()));
        registry.addConverter(new EntityToDtoConverter(getDtoFactory()));
    }

    @Bean
    public AbstractFactory getDtoFactory(){
        return new DtoFactory();
    }

    @Bean
    public AbstractFactory getProductFactory(){
        return new ProductFactory();
    }
}
