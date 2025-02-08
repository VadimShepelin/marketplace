package com.spring.marketplace.service;


import com.spring.marketplace.dto.CreateOrderDto;
import com.spring.marketplace.dto.GetOrderResponse;
import com.spring.marketplace.dto.UpdateOrderStateDto;
import com.spring.marketplace.model.enums.Status;
import java.util.UUID;

public interface OrderService {
    GetOrderResponse createOrder(CreateOrderDto dto);

    Status validateOrder(CreateOrderDto dto);

    GetOrderResponse updateOrderState(UpdateOrderStateDto dto);

    GetOrderResponse getOrderById(UUID id);

    void updateOrderProducts(CreateOrderDto dto);
}
