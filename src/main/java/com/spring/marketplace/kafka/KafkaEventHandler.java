package com.spring.marketplace.kafka;

import com.spring.marketplace.dto.CreateOrderDto;
import com.spring.marketplace.dto.UpdateOrderStateDto;
import com.spring.marketplace.exception.ApplicationException;
import com.spring.marketplace.model.enums.Status;
import com.spring.marketplace.service.OrderService;
import com.spring.marketplace.events.CancelledOrderEvent;
import com.spring.marketplace.events.CompletedOrderEvent;
import com.spring.marketplace.events.CreateOrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@KafkaListener(topics = {"events"}, groupId = "${spring.kafka.consumer.group-id}")
@Slf4j
@RequiredArgsConstructor
public class KafkaEventHandler {

    private final OrderService orderService;

    @KafkaHandler
    public void handleCancelledOrderEvent(CancelledOrderEvent event, @Header(name = "order_id") String orderId){
        log.info("call handleCancelledOrderEvent method with event: {}", event.getEvent());

        UpdateOrderStateDto updateOrderDto = UpdateOrderStateDto.builder()
                .status(Status.CANCELLED)
                .build();

        try {
            orderService.updateOrderState(updateOrderDto, UUID.fromString(orderId));
        }
        catch (ApplicationException ex){
            log.error("Error updating order state. Exception message: {}", ex.getMessage());
        }
    }

    @KafkaHandler
    public void handleCompletedOrderEvent(CompletedOrderEvent event, @Header(name = "order_id") String orderId){
        log.info("call handleCompletedOrderEvent method with event: {}",event.getEvent());

        UpdateOrderStateDto completedOrderDto = UpdateOrderStateDto.builder()
                .status(Status.DONE)
                .build();

        try {
            orderService.updateOrderState(completedOrderDto, UUID.fromString(orderId));
        }
        catch (ApplicationException ex){
            log.error("Error updating order state. Exception message: {}", ex.getMessage());
        }
    }

    @KafkaHandler
    public void handleCreateOrderEvent(CreateOrderEvent event, @Header(name = "user_id") String userId, @Header(name = "idempotency_key") String idempotencyKey){
        log.info("call handleCreateOrderEvent method with event: {}",event.getEvent());

        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .productMap(event.getProductMap())
                .build();

        orderService.createOrder(createOrderDto, UUID.fromString(userId), UUID.fromString(idempotencyKey));
    }
}
