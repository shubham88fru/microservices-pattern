package com.learning.event;

import com.learning.dto.PaymentRequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Data
public class PaymentEvent implements Event {
    private UUID eventId = UUID.randomUUID();
    private LocalDate eventDate = LocalDate.now();
    private PaymentRequestDTO paymentRequestDTO;
    private PaymentStatus paymentStatus;

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public LocalDate getEventDate() {
        return eventDate;
    }

    public PaymentEvent(PaymentRequestDTO paymentRequestDTO, PaymentStatus paymentStatus) {
        this.paymentRequestDTO = paymentRequestDTO;
        this.paymentStatus = paymentStatus;
    }
}
