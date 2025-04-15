package com.zoo.infrastructure.eventhandler;

import com.zoo.domain.event.AnimalMovedEvent;
import com.zoo.domain.event.DomainEvent;
import com.zoo.infrastructure.eventbus.EventSubscriber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnimalMovementEventHandler implements EventSubscriber {

    @Override
    public void handle(DomainEvent event) {
        if (event instanceof AnimalMovedEvent animalMovedEvent) {
            log.info("Animal {} was moved from {} to {}",
                    animalMovedEvent.getAnimal().getName(),
                    animalMovedEvent.getSourceEnclosure() != null
                            ? "Enclosure " + animalMovedEvent.getSourceEnclosure().getId().getValue()
                            : "nowhere",
                    "Enclosure " + animalMovedEvent.getTargetEnclosure().getId().getValue());

            // TODO logging or smt
        }
    }

    @Override
    public boolean isSubscribedTo(Class<? extends DomainEvent> eventType) {
        return AnimalMovedEvent.class.isAssignableFrom(eventType);
    }
}