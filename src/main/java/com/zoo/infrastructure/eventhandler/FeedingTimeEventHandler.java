package com.zoo.infrastructure.eventhandler;

import com.zoo.domain.event.DomainEvent;
import com.zoo.domain.event.FeedingTimeEvent;
import com.zoo.domain.model.Animal;
import com.zoo.domain.model.FeedingSchedule;
import com.zoo.infrastructure.eventbus.EventSubscriber;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
public class FeedingTimeEventHandler implements EventSubscriber {

    @Override
    public void handle(DomainEvent event) {
        if (event instanceof FeedingTimeEvent feedingTimeEvent) {

            String animalNames = feedingTimeEvent.getDueFeedings().stream()
                    .map(FeedingSchedule::getAnimal)
                    .map(Animal::getName)
                    .collect(Collectors.joining(", "));

            log.info("Time to feed animals: {}", animalNames);

            // TODO notifications to someone
        }
    }

    @Override
    public boolean isSubscribedTo(Class<? extends DomainEvent> eventType) {
        return FeedingTimeEvent.class.isAssignableFrom(eventType);
    }
}