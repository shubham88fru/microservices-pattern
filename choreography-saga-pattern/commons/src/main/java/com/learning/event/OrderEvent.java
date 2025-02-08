package com.learning.event;

import com.learning.dto.OrderRequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Data
public class OrderEvent implements Event{
    private UUID eventId = UUID.randomUUID();
    private LocalDate eventDate = LocalDate.now();
    private OrderRequestDTO orderRequestDTO;
    private OrderStatus orderStatus;

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public LocalDate getEventDate() {
        return eventDate;
    }

    public OrderEvent(OrderRequestDTO orderRequestDTO, OrderStatus orderStatus) {
        this.orderRequestDTO = orderRequestDTO;
        this.orderStatus = orderStatus;
    }
}
