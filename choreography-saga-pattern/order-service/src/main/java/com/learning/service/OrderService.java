package com.learning.service;

import com.learning.dto.OrderRequestDTO;
import com.learning.entity.PurchaseOrder;
import com.learning.event.OrderStatus;
import com.learning.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public PurchaseOrder createOrder(OrderRequestDTO orderRequestDTO) {
        PurchaseOrder order = orderRepository.save(convertDTOToEntity(orderRequestDTO));
        orderRequestDTO.setOrderId(order.getId());

        //produce kafka event with status ORDER_CREATED
        orderStatusPublisher.publishOrderEvent(orderRequestDTO, OrderStatus.ORDER_CREATED);

        return order;
    }

    public List<PurchaseOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    private PurchaseOrder convertDTOToEntity(OrderRequestDTO orderRequestDTO) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setProductId(orderRequestDTO.getProductId());
        purchaseOrder.setUserId(orderRequestDTO.getUserId());
        purchaseOrder.setStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(orderRequestDTO.getAmount());

        return purchaseOrder;
    }
}
