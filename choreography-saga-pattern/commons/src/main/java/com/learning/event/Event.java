package com.learning.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface Event {
    UUID getEventId();
    LocalDate getEventDate();
}
