package com.spring.marketplace.service;


import com.spring.marketplace.dto.CreateOrderDto;
import com.spring.marketplace.dto.GetOrderResponse;
import com.spring.marketplace.dto.UpdateOrderStateDto;
import com.spring.marketplace.model.enums.Status;
import java.math.BigDecimal;

public interface OrderService {
    GetOrderResponse createOrder(CreateOrderDto dto);

    Status validateOrder(CreateOrderDto dto, BigDecimal quantity);

    GetOrderResponse updateOrderState(UpdateOrderStateDto dto);
}
