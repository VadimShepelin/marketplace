package com.spring.marketplace.utils.converter;


import com.spring.marketplace.dto.ProductDto;
import com.spring.marketplace.model.Categories;
import com.spring.marketplace.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoToEntityConverter implements Converter<ProductDto, Product> {

    @Override
    public Product convert(ProductDto productDto) {
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
