package com.learning.entity;

import com.learning.event.OrderStatus;
import com.learning.event.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PURCHASE_ORDER_TBL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private int id;
    private int userId;
    private int productId;
    private int price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
