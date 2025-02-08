package com.learning.service;

import com.learning.dto.OrderRequestDTO;
import com.learning.event.OrderEvent;
import com.learning.event.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderSink;

    public void publishOrderEvent(OrderRequestDTO orderRequestDTO, OrderStatus orderStatus) {
        OrderEvent orderEvent = new OrderEvent(orderRequestDTO, orderStatus);
        orderSink.tryEmitNext(orderEvent);
    }
}
