package com.lukaszplawiak;

import java.time.Instant;

public interface DomainEvent {
    Instant getOccurredOn();
}
