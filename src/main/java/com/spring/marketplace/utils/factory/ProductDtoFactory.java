package com.spring.marketplace.utils.factory;

import com.spring.marketplace.dto.ProductDto;
import com.spring.marketplace.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoFactory extends AbstractFactory{

    @Override
    public ProductDto createProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .category(String.valueOf(product.getCategory()))
                .sku(product.getSku())
                .updated_at(product.getUpdatedAt())
                .build();
    }
}
