package com.spring.marketplace.util;

import com.spring.marketplace.dto.CreateProductDto;
import com.spring.marketplace.dto.GetProductResponse;
import com.spring.marketplace.dto.UpdateProductDto;
import com.spring.marketplace.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public class ProductTestUtil {

    public static CreateProductDto createProductDto() {
        return CreateProductDto.builder()
                .sku("TEST SKU")
                .build();
    }

    public static UpdateProductDto createUpdateProductDto() {
        return UpdateProductDto.builder()
                .sku("TEST SKU")
                .name("Updated Name")
                .description("Updated Description")
                .quantity(BigInteger.TEN)
                .price(BigDecimal.valueOf(99.99))
                .build();
    }

    public static Product createProduct() {
        return Product.builder()
                .id(UUID.randomUUID())
                .sku("TEST SKU")
                .build();
    }

    public static GetProductResponse createGetProductResponse() {
        return GetProductResponse.builder()
                .sku("TEST SKU")
                .build();
    }

    public static Page createPage(Product product){
        return new PageImpl(List.of(product));
    }
}
