package com.spring.marketplace.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.spring.marketplace.service.OrderService;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "event")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CancelledOrderEvent.class, name = "cancelled"),
        @JsonSubTypes.Type(value = CompletedOrderEvent.class, name = "completed"),
        @JsonSubTypes.Type(value = CreateOrderEvent.class, name = "created")
})
public interface EventSource{
    default void handleCreateOrderEvent(OrderService service, UUID id, UUID key){}
    default void handleChangeOrderStatusEvent(OrderService service, UUID id){}
}
