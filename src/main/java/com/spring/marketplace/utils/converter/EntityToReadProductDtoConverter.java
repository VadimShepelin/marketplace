package com.spring.marketplace.utils.converter;

import com.spring.marketplace.dto.ReadProductDto;
import com.spring.marketplace.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToReadProductDtoConverter implements Converter<Product, ReadProductDto> {

    @Override
    public ReadProductDto convert(Product product) {
        return ReadProductDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .sku(product.getSku())
                .updated_at(product.getUpdatedAt())
                .build();
    }
}
