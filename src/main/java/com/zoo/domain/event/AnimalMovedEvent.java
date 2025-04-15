package com.zoo.domain.event;

import com.zoo.domain.model.Animal;
import com.zoo.domain.model.Enclosure;
import lombok.Getter;

@Getter
public class AnimalMovedEvent extends DomainEvent {
    private final Animal animal;
    private final Enclosure sourceEnclosure;
    private final Enclosure targetEnclosure;

    public AnimalMovedEvent(Animal animal, Enclosure sourceEnclosure, Enclosure targetEnclosure) {
        super();
        this.animal = animal;
        this.sourceEnclosure = sourceEnclosure;
        this.targetEnclosure = targetEnclosure;
    }
}