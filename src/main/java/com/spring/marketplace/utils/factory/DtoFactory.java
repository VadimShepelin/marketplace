package com.spring.marketplace.utils.factory;

import com.spring.marketplace.dto.ProductReadDto;
import com.spring.marketplace.model.entity.Product;

public class DtoFactory extends AbstractFactory{

    @Override
    public ProductReadDto createProductReadDto(Product product) {
        return ProductReadDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .category(String.valueOf(product.getCategory()))
                .sku(product.getSku())
                .updated_at(product.getUpdated_at())
                .build();
    }
}
