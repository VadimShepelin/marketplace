package com.spring.marketplace.events;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.spring.marketplace.dto.UpdateOrderStateDto;
import com.spring.marketplace.model.enums.Status;
import com.spring.marketplace.service.OrderService;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@NotNull
@Setter
@Getter
@JsonTypeName("completed")
public class CompletedOrderEvent implements EventSource {

    private Event event = Event.COMPLETED_ORDER;

    @Override
    public void handleChangeOrderStatusEvent(OrderService service, UUID id) {
        UpdateOrderStateDto updateOrderDto = UpdateOrderStateDto.builder()
                .status(Status.DONE)
                .build();

        service.updateOrderState(updateOrderDto, id);
    }
}
