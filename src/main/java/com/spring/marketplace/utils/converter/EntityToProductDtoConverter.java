package com.spring.marketplace.utils.converter;

import com.spring.marketplace.dto.ProductDto;
import com.spring.marketplace.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EntityToProductDtoConverter implements Converter<Product, ProductDto> {

    @Override
    public ProductDto convert(Product product) {
        return ProductDto.builder()
                .id(product.getId())
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
