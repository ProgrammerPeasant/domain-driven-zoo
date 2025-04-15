package com.zoo.infrastructure.eventbus;

import com.zoo.domain.event.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryEventBus implements EventBus {
    private final Map<Class<? extends DomainEvent>, List<EventSubscriber>> subscribers = new HashMap<>();

    @Override
    public void publish(DomainEvent event) {
        log.info("Publishing event: {}", event.getClass().getSimpleName());
        Class<? extends DomainEvent> eventType = event.getClass();

        List<EventSubscriber> allSubscribers = new ArrayList<>();
        if (subscribers.containsKey(eventType)) {
            allSubscribers.addAll(subscribers.get(eventType));
        }

        for (Map.Entry<Class<? extends DomainEvent>, List<EventSubscriber>> entry : subscribers.entrySet()) {
            if (entry.getKey().isAssignableFrom(eventType) && entry.getKey() != eventType) {
                allSubscribers.addAll(entry.getValue().stream()
                        .filter(sub -> sub.isSubscribedTo(eventType))
                        .toList());
            }
        }

        for (EventSubscriber subscriber : allSubscribers) {
            try {
                subscriber.handle(event);
            } catch (Exception e) {
                log.error("Error handling event {} by subscriber {}: {}",
                        eventType.getSimpleName(), subscriber.getClass().getSimpleName(), e.getMessage());
            }
        }
    }

    @Override
    public void subscribe(Class<? extends DomainEvent> eventType, EventSubscriber subscriber) {
        log.info("Subscribing {} to event {}",
                subscriber.getClass().getSimpleName(), eventType.getSimpleName());
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(subscriber);
    }

    @Override
    public List<EventSubscriber> getSubscribers(Class<? extends DomainEvent> eventType) {
        return subscribers.getOrDefault(eventType, new ArrayList<>());
    }
}