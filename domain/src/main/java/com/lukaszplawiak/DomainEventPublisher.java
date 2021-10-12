package com.lukaszplawiak;

import com.lukaszplawiak.DomainEvent;

public interface DomainEventPublisher {
    void publisher(DomainEvent event);
}
