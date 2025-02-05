package com.spring.marketplace.service.impl;

import com.spring.marketplace.dto.CreateOrderDto;
import com.spring.marketplace.dto.GetOrderResponse;
import com.spring.marketplace.dto.GetProductResponse;
import com.spring.marketplace.dto.UpdateOrderStateDto;
import com.spring.marketplace.exception.ApplicationException;
import com.spring.marketplace.model.Order;
import com.spring.marketplace.model.User;
import com.spring.marketplace.model.enums.Status;
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
import java.util.List;

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
        List<GetProductResponse> allProductsList = productService.findAllProductsBySkus(dto.getProductMap().keySet().toArray(String[]::new));
        BigDecimal totalProductsPrice = allProductsList.
                stream().map(GetProductResponse::getPrice).
                reduce(BigDecimal.ZERO, BigDecimal::add);
        Status orderStatus = validateOrder(dto,totalProductsPrice);

        if(orderStatus==Status.APPROVED) {
            dto.getProductMap().forEach(productService::updateProductQuantity);
            userService.reduceUserBalance(dto.getUser_id(), totalProductsPrice);
            log.info("Update balance and quantity was successful");
            orderStatus = Status.DONE;
        }

        Order order = Order.builder()
                .totalCost(totalProductsPrice)
                .status(orderStatus)
                .user(userService.getUserById(dto.getUser_id()))
                .build();

        log.info("Save order: {}", order);
        return conversionService.convert(orderRepository.save(order), GetOrderResponse.class);
    }


    @Override
    @Transactional
    public Status validateOrder(CreateOrderDto dto, BigDecimal totalProductsPrice) {
        Status orderStatus = dto.getProductMap().entrySet().stream().
                filter((item) -> productService.getProductBySku(item.getKey()).getQuantity().compareTo(item.getValue())<0)
                .findAny().map((element) -> Status.REJECTED).orElse(Status.APPROVED);

        User user = userService.getUserById(dto.getUser_id());
        if (user.getBalance().compareTo(totalProductsPrice) < 0) {
            log.error("Not enough balance to buy this products");
            return Status.REJECTED;
        }

        return orderStatus;
    }

    @Override
    @Transactional
    public GetOrderResponse updateOrderState(UpdateOrderStateDto dto){
        return orderRepository.findOrderByCompositeId(dto.getCompositeOrderId().getId(),dto.getCompositeOrderId().getUser())
                .map((item) -> {
                    if(dto.getStatus()==Status.REJECTED && item.getStatus()==Status.DONE ) {
                        item.setStatus(Status.REJECTED);
                        log.info("Change order status to REJECTED");
                        userService.increaseUserBalance(item.getUser().getId(),item.getTotalCost());
                        return conversionService.convert(orderRepository.save(item), GetOrderResponse.class);
                    }
                    else{
                        log.error("Failed to change order status");
                        throw new ApplicationException(ErrorType.FAILED_TO_CHANGE_ORDER_STATUS);
                    }
                }).orElseThrow(() -> {
                    log.error("No such order");
                    return new ApplicationException(ErrorType.NOT_SUCH_ORDER);
                });
    }
}
