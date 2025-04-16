package com.zoo.infrastructure.config;

import com.zoo.domain.event.AnimalMovedEvent;
import com.zoo.domain.event.FeedingTimeEvent;
import com.zoo.infrastructure.eventbus.EventBus;
import com.zoo.infrastructure.eventhandler.AnimalMovementEventHandler;
import com.zoo.infrastructure.eventhandler.FeedingTimeEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventListenersConfig {

    @Bean
    public AnimalMovementEventHandler animalMovementEventHandler(EventBus eventBus) {
        AnimalMovementEventHandler handler = new AnimalMovementEventHandler();
        eventBus.subscribe(AnimalMovedEvent.class, handler);
        return handler;
    }

    @Bean
    public FeedingTimeEventHandler feedingTimeEventHandler(EventBus eventBus) {
        FeedingTimeEventHandler handler = new FeedingTimeEventHandler();
        eventBus.subscribe(FeedingTimeEvent.class, handler);
        return handler;
    }
}