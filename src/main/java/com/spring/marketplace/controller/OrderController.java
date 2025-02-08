package com.spring.marketplace.controller;

import com.spring.marketplace.dto.CreateOrderDto;
import com.spring.marketplace.dto.GetOrderResponse;
import com.spring.marketplace.dto.UpdateOrderStateDto;
import com.spring.marketplace.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public GetOrderResponse createOrder(@NotNull @RequestParam("user_id") UUID userId, @Valid @RequestBody CreateOrderDto dto) {
        dto.setUser_id(userId);

        return orderService.createOrder(dto);
    }

    @PutMapping
    public GetOrderResponse changeOrderState(@NotNull @RequestParam("order_id") UUID orderId, @Valid @RequestBody UpdateOrderStateDto dto){
        dto.setOrderId(orderId);

        return orderService.updateOrderState(dto);
    }
}
