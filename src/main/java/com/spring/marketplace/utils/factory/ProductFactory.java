package com.spring.marketplace.utils.factory;

import com.spring.marketplace.dto.ProductDto;
import com.spring.marketplace.model.entity.Product;
import com.spring.marketplace.model.enums.Categories;


public class ProductFactory extends AbstractFactory{

    @Override
    public Product createProduct(ProductDto productDto) {
            return Product.builder()
                    .id(productDto.getId())
                    .name(productDto.getName())
                    .price(productDto.getPrice())
                    .description(productDto.getDescription())
                    .createdAt(productDto.getCreatedAt())
                    .sku(productDto.getSku())
                    .createdAt(productDto.getCreatedAt())
                    .updated_at(productDto.getUpdated_at())
                    .quantity(productDto.getQuantity())
                    .category(Enum.valueOf(Categories.class,productDto.getCategory()))
                    .build();
    }
}
