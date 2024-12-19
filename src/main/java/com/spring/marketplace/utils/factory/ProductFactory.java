package com.spring.marketplace.utils.factory;

import com.spring.marketplace.dto.ProductReadDto;
import com.spring.marketplace.model.entity.Product;
import com.spring.marketplace.model.enums.Categories;
import org.springframework.stereotype.Component;


public class ProductFactory extends AbstractFactory{

    @Override
    public Product createProduct(Object object) {
        if(object instanceof ProductReadDto){
            ProductReadDto productRequestDto = (ProductReadDto) object;

            return Product.builder()
                    .id(productRequestDto.getId())
                    .name(productRequestDto.getName())
                    .price(productRequestDto.getPrice())
                    .description(productRequestDto.getDescription())
                    .createdAt(productRequestDto.getCreatedAt())
                    .sku(productRequestDto.getSku())
                    .createdAt(productRequestDto.getCreatedAt())
                    .updated_at(productRequestDto.getUpdated_at())
                    .quantity(productRequestDto.getQuantity())
                    .category(Enum.valueOf(Categories.class,productRequestDto.getCategory()))
                    .build();

        }

        return null;
    }
}
