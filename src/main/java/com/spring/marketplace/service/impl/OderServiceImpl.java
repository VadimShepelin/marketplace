package com.spring.marketplace.service.impl;

import com.spring.marketplace.dto.CreateOrderDto;
import com.spring.marketplace.dto.GetOrderResponse;
import com.spring.marketplace.dto.GetProductResponse;
import com.spring.marketplace.dto.UpdateOrderStateDto;
import com.spring.marketplace.exception.ApplicationException;
import com.spring.marketplace.model.Order;
import com.spring.marketplace.model.User;
import com.spring.marketplace.repository.OrderRepository;
import com.spring.marketplace.service.OrderService;
import com.spring.marketplace.service.ProductService;
import com.spring.marketplace.service.UserService;
import com.spring.marketplace.utils.enums.ErrorType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class OderServiceImpl implements OrderService {

    private final ProductService productService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ConversionService conversionService;

    @Override
    @Transactional
    public GetOrderResponse createOrder(CreateOrderDto dto) {
        BigDecimal orderTotalPrice = validateOrder(dto);
        log.info("Order validation was successful");

        Order order = Order.builder()
                .totalCost(orderTotalPrice)
                .user(userService.getUserById(dto.getUser_id()))
                .build();

        log.info("Save order: {}", order);
        return conversionService.convert(orderRepository.save(order), GetOrderResponse.class);
    }


    @Override
    @Transactional
    public BigDecimal validateOrder(CreateOrderDto dto) {
        BigDecimal totalProductPrice = dto.getProductMap().entrySet().stream()
                .map(entry -> {
                    GetProductResponse product = productService.getProductBySku(entry.getKey());

                    if (product.getQuantity().compareTo(entry.getValue()) < 0) {
                        log.error("Invalid product quantity");
                        throw new ApplicationException(ErrorType.INSUFFICIENT_QUANTITY_OF_PRODUCTS);
                    }

                    productService.updateProductQuantity(entry.getKey(), entry.getValue());
                    return product.getPrice().multiply(new BigDecimal(entry.getValue()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        User user = userService.getUserById(dto.getUser_id());
        if (user.getBalance().compareTo(totalProductPrice) < 0) {
            log.error("Not enough balance to buy this products");
            throw new ApplicationException(ErrorType.NOT_ENOUGH_BALANCE);
        }

        userService.updateUserBalance(user.getId(), totalProductPrice);
        return totalProductPrice;
    }

    @Override
    @Transactional
    public GetOrderResponse updateOrderState(UpdateOrderStateDto dto){
        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(
                () -> {
                    log.error("Order not found");
                    return new ApplicationException(ErrorType.NOT_SUCH_ORDER);
                });
        order.setStatus(dto.getStatus());

        log.info("Update order: {}", order);
        return conversionService.convert(orderRepository.save(order),GetOrderResponse.class);
    }
}
