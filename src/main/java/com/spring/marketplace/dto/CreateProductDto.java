package com.spring.marketplace.dto;


import com.spring.marketplace.model.Categories;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Builder;

@ToString(exclude = "category")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class CreateProductDto {

    @NotBlank(message = "Name must be not empty")
    private String name;

    @NotBlank(message = "Description must be not empty")
    @Size(min = 10, message = "Description length min 10 chars")
    private String description;

    @NotNull(message = "Category must be not empty")
    private Categories category;

    @Min(value = 100, message = "Min price is 100")
    private BigDecimal price;

    private BigInteger quantity;

    @NotBlank(message = "SKU must be not empty")
    private String sku;

}
