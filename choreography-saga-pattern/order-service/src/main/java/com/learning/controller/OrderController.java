package com.learning.controller;

import com.learning.dto.OrderRequestDTO;
import com.learning.entity.PurchaseOrder;
import com.learning.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public PurchaseOrder createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(orderRequestDTO);
    }

    @GetMapping
    public List<PurchaseOrder> getOrders() {
        return orderService.getAllOrders();
    }
}
