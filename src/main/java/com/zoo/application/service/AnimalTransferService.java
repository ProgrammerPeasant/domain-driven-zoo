package com.zoo.application.service;

import com.zoo.domain.event.AnimalMovedEvent;
import com.zoo.domain.model.Animal;
import com.zoo.domain.model.Enclosure;
import com.zoo.domain.repository.AnimalRepository;
import com.zoo.domain.repository.EnclosureRepository;
import com.zoo.domain.valueobject.AnimalId;
import com.zoo.domain.valueobject.EnclosureId;
import com.zoo.infrastructure.eventbus.EventBus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AnimalTransferService {
    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;
    private final EventBus eventBus;

    public AnimalTransferService(
            AnimalRepository animalRepository,
            EnclosureRepository enclosureRepository,
            EventBus eventBus
    ) {
        this.animalRepository = animalRepository;
        this.enclosureRepository = enclosureRepository;
        this.eventBus = eventBus;
    }

    public boolean transferAnimal(UUID animalId, UUID targetEnclosureId) {
        Optional<Animal> animalOpt = animalRepository.findById(new AnimalId(animalId));
        Optional<Enclosure> enclosureOpt = enclosureRepository.findById(new EnclosureId(targetEnclosureId));

        if (animalOpt.isEmpty() || enclosureOpt.isEmpty()) {
            return false;
        }

        Animal animal = animalOpt.get();
        Enclosure targetEnclosure = enclosureOpt.get();
        Enclosure sourceEnclosure = animal.getEnclosure();

        if (animal.moveToEnclosure(targetEnclosure)) {
            animalRepository.save(animal);
            enclosureRepository.save(targetEnclosure);
            if (sourceEnclosure != null) {
                enclosureRepository.save(sourceEnclosure);
            }

            eventBus.publish(new AnimalMovedEvent(animal, sourceEnclosure, targetEnclosure));
            return true;
        }

        return false;
    }
}