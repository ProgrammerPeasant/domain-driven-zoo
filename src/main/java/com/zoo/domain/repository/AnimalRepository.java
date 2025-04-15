package com.zoo.domain.repository;

import com.zoo.domain.model.Animal;
import com.zoo.domain.valueobject.AnimalId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface AnimalRepository {
    Animal save(Animal animal);
    Optional<Animal> findById(AnimalId id);
    List<Animal> findAll();
    void delete(Animal animal);
    List<Animal> findByEnclosureId(UUID enclosureId);
}