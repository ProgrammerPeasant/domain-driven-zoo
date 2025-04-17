package com.zoo.domain.model;

import com.zoo.domain.valueobject.EnclosureId;
import com.zoo.domain.valueobject.EnclosureType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
public class Enclosure {
    private final EnclosureId id;
    private final EnclosureType type;
    private final double size;
    private final int maxCapacity;
    private final List<Animal> animals;
    private boolean cleaningNeeded;

    public Enclosure(EnclosureType type, double size, int maxCapacity) {
        this.id = new EnclosureId(UUID.randomUUID());
        this.type = type;
        this.size = size;
        this.maxCapacity = maxCapacity;
        this.animals = new ArrayList<>();
        this.cleaningNeeded = false;
    }

    public boolean addAnimal(Animal animal) {
        if (animals.size() >= maxCapacity) {
            return false;
        }

        if (!isCompatible(animal)) {
            return false;
        }

        animals.add(animal);
        cleaningNeeded = true;
        return true;
    }

    public boolean removeAnimal(Animal animal) {
        return animals.remove(animal);
    }

    public void clean() {
        cleaningNeeded = false;
    }

    public int getCurrentAnimalCount() {
        return animals.size();
    }

    public boolean isFull() {
        return animals.size() >= maxCapacity;
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    private boolean isCompatible(Animal animal) {
        return switch (type) {
            case PREDATOR -> animal.getSpecies().contains("Tiger") ||
                    animal.getSpecies().contains("Lion") ||
                    animal.getSpecies().contains("Wolf");
            case HERBIVORE -> animal.getSpecies().contains("Deer") ||
                    animal.getSpecies().contains("Elephant") ||
                    animal.getSpecies().contains("Giraffe");
            case BIRD -> animal.getSpecies().contains("Eagle") ||
                    animal.getSpecies().contains("Parrot") ||
                    animal.getSpecies().contains("Penguin");
            case AQUARIUM -> animal.getSpecies().contains("Fish") ||
                    animal.getSpecies().contains("Shark") ||
                    animal.getSpecies().contains("Dolphin");
        };
    }
}