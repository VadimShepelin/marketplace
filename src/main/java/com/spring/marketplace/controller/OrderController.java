package com.spring.marketplace.controller;

import com.spring.marketplace.dto.CreateOrderDto;
import com.spring.marketplace.dto.GetOrderResponse;
import com.spring.marketplace.dto.OrderWithProductsResponse;
import com.spring.marketplace.dto.UpdateOrderStateDto;
import com.spring.marketplace.service.OrderService;
import com.spring.marketplace.events.EventSource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public GetOrderResponse createOrder(@NotNull @RequestParam("user_id") UUID userId, @NotNull @RequestHeader("idempotency_key") UUID idempotencyKey, @Valid @RequestBody CreateOrderDto dto) {
        return orderService.createOrder(dto,userId,idempotencyKey);
    }

    @PutMapping("/state")
    public GetOrderResponse changeOrderState(@NotNull @RequestParam("order_id") UUID orderId, @Valid @RequestBody UpdateOrderStateDto dto){
        return orderService.updateOrderState(dto,orderId);
    }

    @PutMapping
    public ResponseEntity<String> addProductsToOrder(@NotNull @RequestParam("order_id") UUID orderId, @Valid @RequestBody CreateOrderDto dto){
        orderService.updateOrderProducts(dto,orderId);

        return ResponseEntity.ok("Add new products to order");
    }

    @GetMapping
    public List<OrderWithProductsResponse> getOrdersWithProducts() {
        return orderService.getOrdersWithProducts();
    }

    @PostMapping("/handle")
    public ResponseEntity<String> handleCreateOrderEvent(@Valid @RequestBody EventSource eventSource, @NotNull @RequestParam("user_id") UUID userId, @NotNull @RequestHeader("idempotency_key") UUID idempotencyKey) {
        orderService.handleCreateOrderEvent(eventSource, userId, idempotencyKey);

        return ResponseEntity.ok("Create order event handled successfully");
    }

    @PutMapping("/handle")
    public ResponseEntity<String> handleChangeOrderStatusEvent(@Valid @RequestBody EventSource eventSource, @NotNull @RequestParam("order_id") UUID orderId) {
        orderService.handleChangeOrderStatusEvent(eventSource, orderId);

        return ResponseEntity.ok("Change order status event handled successfully");
    }


}
