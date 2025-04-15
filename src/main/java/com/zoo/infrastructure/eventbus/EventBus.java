package com.zoo.infrastructure.eventbus;

import com.zoo.domain.event.DomainEvent;

import java.util.List;

public interface EventBus {
    void publish(DomainEvent event);
    void subscribe(Class<? extends DomainEvent> eventType, EventSubscriber subscriber);
    List<EventSubscriber> getSubscribers(Class<? extends DomainEvent> eventType);
}