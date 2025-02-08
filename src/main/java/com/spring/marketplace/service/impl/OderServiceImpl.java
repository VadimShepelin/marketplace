package com.spring.marketplace.service.impl;

import com.spring.marketplace.dto.CreateOrderDto;
import com.spring.marketplace.dto.GetOrderResponse;
import com.spring.marketplace.dto.UpdateOrderStateDto;
import com.spring.marketplace.exception.ApplicationException;
import com.spring.marketplace.model.Order;
import com.spring.marketplace.model.OrderItems;
import com.spring.marketplace.model.enums.Status;
import com.spring.marketplace.repository.OrderItemsRepository;
import com.spring.marketplace.repository.OrderRepository;
import com.spring.marketplace.service.OrderService;
import com.spring.marketplace.service.ProductService;
import com.spring.marketplace.service.UserService;
import com.spring.marketplace.utils.enums.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OderServiceImpl implements OrderService {

    private final ProductService productService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ConversionService conversionService;
    private final OrderItemsRepository orderItemsRepository;

    @Override
    @Transactional
    public GetOrderResponse createOrder(CreateOrderDto dto) {
        Status orderStatus = validateOrder(dto);

        Order order = Order.builder()
                .status(orderStatus)
                .order_id(UUID.randomUUID())
                .user(userService.getUserById(dto.getUser_id()))
                .build();

        double orderTotalPrice = dto.getProductMap().entrySet().stream()
                        .mapToDouble((item) -> {
                            orderItemsRepository.save(OrderItems.builder()
                                    .sku(item.getKey())
                                    .quantity(item.getValue())
                                    .orderId(order.getOrder_id())
                                    .build());

                            return item.getValue().doubleValue() *
                                productService.getProductBySku(item.getKey()).getPrice().doubleValue();
                        }).sum();

        dto.getProductMap().forEach(productService::reduceProductQuantity);
        log.info("Update Quantity was successful");

        order.setTotalCost(BigDecimal.valueOf(orderTotalPrice));

        log.info("Save order: {}", order);
        return conversionService.convert(orderRepository.save(order), GetOrderResponse.class);
    }


    @Override
    @Transactional(readOnly = true)
    public Status validateOrder(CreateOrderDto dto) {
        dto.getProductMap().entrySet().stream().
                filter((item) -> productService.getProductBySku(item.getKey()).getQuantity().compareTo(item.getValue())<0)
                .findAny().ifPresent((element) -> {
                    log.info("Insufficient quantity of products");
                    throw new ApplicationException(ErrorType.INSUFFICIENT_QUANTITY_OF_PRODUCTS);
                });

        return Status.CREATED;
    }

    @Override
    @Transactional
    public GetOrderResponse updateOrderState(UpdateOrderStateDto dto){
        return orderRepository.findOrderById(dto.getOrderId())
                .map((item) -> {
                    if(dto.getStatus()==Status.DONE && item.getStatus() == Status.CREATED) {
                        item.setStatus(Status.DONE);
                        log.info("Change order status to DONE");
                        return conversionService.convert(orderRepository.save(item), GetOrderResponse.class);
                    }

                    else if(dto.getStatus()==Status.REJECTED && item.getStatus() == Status.CREATED) {
                        orderItemsRepository.findAllByOrderId(dto.getOrderId())
                                        .forEach((element) -> productService.increaseProductQuantity(element.getSku(),element.getQuantity()));

                        item.setStatus(Status.REJECTED);
                        log.info("Change order status to REJECTED");
                        return conversionService.convert(orderRepository.save(item), GetOrderResponse.class);
                    }

                    log.error("Failed to change order status");
                    throw new ApplicationException(ErrorType.FAILED_TO_CHANGE_ORDER_STATUS);
                }).orElseThrow(() -> {
                    log.error("No such order");
                    return new ApplicationException(ErrorType.NOT_SUCH_ORDER);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public GetOrderResponse getOrderById(UUID id) {
        return orderRepository.findById(id).map(
                (item) -> conversionService.convert(item, GetOrderResponse.class)
        ).orElseThrow(() -> {
            log.error("No such order");
            return new ApplicationException(ErrorType.NOT_SUCH_ORDER);
        });
    }
}
