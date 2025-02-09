package com.learning.service;

import com.learning.dto.OrderRequestDTO;
import com.learning.dto.PaymentRequestDTO;
import com.learning.entity.UserBalance;
import com.learning.entity.UserTransaction;
import com.learning.event.OrderEvent;
import com.learning.event.PaymentEvent;
import com.learning.event.PaymentStatus;
import com.learning.repository.UserBalanceRepository;
import com.learning.repository.UserTransactionRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @PostConstruct
    public void initUserBalance() {
        userBalanceRepository.saveAll(Stream.of(new UserBalance(101, 5000),
                new UserBalance(102, 3000),
                new UserBalance(103, 4200),
                new UserBalance(104, 20000),
                new UserBalance(105, 999)
                ).toList());
    }

    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDTO orderRequestDTO = orderEvent.getOrderRequestDTO();
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(orderRequestDTO.getOrderId(),
                orderRequestDTO.getUserId(), orderRequestDTO.getAmount());

        return userBalanceRepository.findById(orderRequestDTO.getUserId())
                .filter(userBalance -> userBalance.getPrice() > orderRequestDTO.getAmount())
                .map(userBalance -> {
                    userBalance.setPrice(userBalance.getPrice()-orderRequestDTO.getAmount());
                    userTransactionRepository.save(new UserTransaction(orderRequestDTO.getOrderId(),
                            orderRequestDTO.getUserId(), orderRequestDTO.getAmount()));

                    return new PaymentEvent(paymentRequestDTO, PaymentStatus.PAYMENT_SUCCESS);
                }).orElse(new PaymentEvent(paymentRequestDTO, PaymentStatus.PAYMENT_FAILED));

    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userTransactionRepository.findById(orderEvent.getOrderRequestDTO().getOrderId())
                .ifPresent(userTransaction -> {
                    userTransactionRepository.delete(userTransaction);
                    userTransactionRepository.findById(userTransaction.getUserId())
                            .ifPresent(userBalance -> {
                                userBalance.setAmount(userBalance.getAmount()+userBalance.getAmount());
                            });
                });
    }
}
