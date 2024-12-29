package com.spring.marketplace.utils.converter;


import com.spring.marketplace.dto.UpdateProductDto;
import com.spring.marketplace.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UpdateProductDtoToEntityConverter implements Converter<UpdateProductDto, Product> {

    @Override
    public Product convert(UpdateProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .sku(productDto.getSku())
                .updatedAt(LocalDateTime.now())
                .quantity(productDto.getQuantity())
                .category(productDto.getCategory())
                .build();
    }


}
