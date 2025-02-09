package com.learning.config;

import com.learning.dto.OrderRequestDTO;
import com.learning.dto.PaymentRequestDTO;
import com.learning.entity.PurchaseOrder;
import com.learning.event.OrderStatus;
import com.learning.event.PaymentStatus;
import com.learning.repository.OrderRepository;
import com.learning.service.OrderStatusPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Configuration
public class OrderStatusUpdateHandler {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public void updateOrder(int id, Consumer<PurchaseOrder> consumer) {
        orderRepository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
    }

    public void updateOrder(PurchaseOrder order) {
        boolean isComplete = PaymentStatus.PAYMENT_SUCCESS == order.getPaymentStatus();
        OrderStatus orderStatus = isComplete ? OrderStatus.ORDER_COMPLETED: OrderStatus.ORDER_CANCELED;
        order.setStatus(orderStatus);

        if ((!isComplete)) {
            orderStatusPublisher.publishOrderEvent(convertEntityToDTO(order), orderStatus);
        }
    }

    public OrderRequestDTO convertEntityToDTO(PurchaseOrder order) {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setOrderId(order.getId());
        orderRequestDTO.setUserId(order.getUserId());
        orderRequestDTO.setAmount(order.getPrice());
        orderRequestDTO.setProductId(order.getProductId());

        return orderRequestDTO;
    }
}
