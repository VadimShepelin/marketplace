package com.spring.marketplace.dto;


import com.spring.marketplace.model.enums.Categories;
import lombok.*;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString(exclude = "category")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ProductReadDto {

    private UUID id;

    private String name;

    private String description;

    private String category;

    private double price;

    private BigInteger quantity;

    private String sku;

    private Instant createdAt;

    private LocalDateTime updated_at;
}
