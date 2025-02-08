package com.learning.dto;

import com.learning.event.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private int userId;
    private int productId;
    private int amount;
    private int orderId;
    private OrderStatus orderStatus;
}
