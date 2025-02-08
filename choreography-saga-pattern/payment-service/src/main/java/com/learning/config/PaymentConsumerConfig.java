package com.learning.config;

import com.learning.event.OrderEvent;
import com.learning.event.OrderStatus;
import com.learning.event.PaymentEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class PaymentConsumerConfig {

    @Bean
    public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor() {
        return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
    }

    private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {
        //get the user id
        //check the balance availability
        //if balance sufficient -> Payment completed and deduct amount from db.
        //if payment not sufficient -> cancel the order event and update the amount in DB.
        if (OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())) {
            return Mono.fromSupplier(() -> null /*TODO*/);
        }

        return null;
    }
}
