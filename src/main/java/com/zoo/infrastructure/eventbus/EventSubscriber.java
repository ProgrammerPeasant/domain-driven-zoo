package com.zoo.infrastructure.eventbus;

import com.zoo.domain.event.DomainEvent;

public interface EventSubscriber {
    void handle(DomainEvent event);
    boolean isSubscribedTo(Class<? extends DomainEvent> eventType);
}