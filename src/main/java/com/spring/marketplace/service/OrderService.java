package com.spring.marketplace.service;


import com.spring.marketplace.dto.CreateOrderDto;
import com.spring.marketplace.dto.GetOrderResponse;
import com.spring.marketplace.dto.UpdateOrderStateDto;
import java.math.BigDecimal;

public interface OrderService {
    GetOrderResponse createOrder(CreateOrderDto dto);

    BigDecimal validateOrder(CreateOrderDto dto);

    GetOrderResponse updateOrderState(UpdateOrderStateDto dto);
}
