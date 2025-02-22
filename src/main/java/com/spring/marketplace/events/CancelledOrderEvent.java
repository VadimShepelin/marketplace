package com.spring.marketplace.events;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.spring.marketplace.dto.UpdateOrderStateDto;
import com.spring.marketplace.model.enums.Status;
import com.spring.marketplace.service.OrderService;
import lombok.*;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("cancelled")
public class CancelledOrderEvent implements EventSource {

    private Event event = Event.CANCELLED_ORDER;

    @Override
    public void handleChangeOrderStatusEvent(OrderService service, UUID id) {
        UpdateOrderStateDto updateOrderDto = UpdateOrderStateDto.builder()
                .status(Status.CANCELLED)
                .build();

        service.updateOrderState(updateOrderDto, id);
    }
}
