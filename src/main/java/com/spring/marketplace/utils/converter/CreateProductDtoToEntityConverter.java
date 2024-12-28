package com.spring.marketplace.utils.converter;


import com.spring.marketplace.dto.CreateProductDto;
import com.spring.marketplace.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateProductDtoToEntityConverter implements Converter<CreateProductDto, Product> {

    @Override
    public Product convert(CreateProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .createdAt(productDto.getCreatedAt())
                .sku(productDto.getSku())
                .createdAt(productDto.getCreatedAt())
                .updatedAt(productDto.getUpdated_at())
                .quantity(productDto.getQuantity())
                .category(productDto.getCategory())
                .build();
    }


}
