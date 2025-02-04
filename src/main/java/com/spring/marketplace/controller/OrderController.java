package com.spring.marketplace.controller;

import com.spring.marketplace.dto.CreateOrderDto;
import com.spring.marketplace.dto.GetOrderResponse;
import com.spring.marketplace.dto.UpdateOrderStateDto;
import com.spring.marketplace.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public GetOrderResponse createOrder(@Valid @RequestBody CreateOrderDto dto) {
        return orderService.createOrder(dto);
    }

    @PutMapping
    public GetOrderResponse changeOrderState(@Valid @RequestBody UpdateOrderStateDto dto){
        return orderService.updateOrderState(dto);
    }
}
