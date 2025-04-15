package com.zoo.infrastructure.repository;

import com.zoo.domain.model.Animal;
import com.zoo.domain.repository.AnimalRepository;
import com.zoo.domain.valueobject.AnimalId;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryAnimalRepository implements AnimalRepository {
    private final Map<UUID, Animal> animals = new HashMap<>();

    @Override
    public Animal save(Animal animal) {
        animals.put(animal.getId().getValue(), animal);
        return animal;
    }

    @Override
    public Optional<Animal> findById(AnimalId id) {
        return Optional.ofNullable(animals.get(id.getValue()));
    }

    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(animals.values());
    }

    @Override
    public void delete(Animal animal) {
        animals.remove(animal.getId().getValue());
    }

    @Override
    public List<Animal> findByEnclosureId(UUID enclosureId) {
        return animals.values().stream()
                .filter(animal -> animal.getEnclosure() != null &&
                        animal.getEnclosure().getId().getValue().equals(enclosureId))
                .collect(Collectors.toList());
    }
}