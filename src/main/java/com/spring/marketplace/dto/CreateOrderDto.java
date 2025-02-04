package com.spring.marketplace.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigInteger;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto {

    @NotNull(message = "User id must be not null")
    private UUID user_id;

    @NotNull(message = "No products sku or their quantity were transferred")
    private Map<String, BigInteger> productMap;
}
