package com.spring.marketplace.events;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.spring.marketplace.dto.CreateOrderDto;
import com.spring.marketplace.service.OrderService;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigInteger;
import java.util.Map;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("created")
public class CreateOrderEvent implements EventSource {

    @NotNull(message = "No products sku or their quantity were transferred")
    private Map<String, BigInteger> productMap;

    private Event event = Event.CREATE_ORDER;

    @Override
    public void handleCreateOrderEvent(OrderService service, UUID id, UUID key) {
        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .productMap(this.getProductMap())
                .build();

        service.createOrder(createOrderDto, id, key);
    }
}
