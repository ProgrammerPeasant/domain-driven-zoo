package com.zoo.infrastructure.repository;

import com.zoo.domain.model.Enclosure;
import com.zoo.domain.repository.EnclosureRepository;
import com.zoo.domain.valueobject.EnclosureId;
import com.zoo.domain.valueobject.EnclosureType;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryEnclosureRepository implements EnclosureRepository {
    private final Map<UUID, Enclosure> enclosures = new HashMap<>();

    @Override
    public Enclosure save(Enclosure enclosure) {
        enclosures.put(enclosure.getId().getValue(), enclosure);
        return enclosure;
    }

    @Override
    public Optional<Enclosure> findById(EnclosureId id) {
        return Optional.ofNullable(enclosures.get(id.getValue()));
    }

    @Override
    public List<Enclosure> findAll() {
        return new ArrayList<>(enclosures.values());
    }

    @Override
    public void delete(Enclosure enclosure) {
        enclosures.remove(enclosure.getId().getValue());
    }

    @Override
    public List<Enclosure> findByType(EnclosureType type) {
        return enclosures.values().stream()
                .filter(enclosure -> enclosure.getType() == type)
                .collect(Collectors.toList());
    }

    @Override
    public List<Enclosure> findAvailable() {
        return enclosures.values().stream()
                .filter(enclosure -> !enclosure.isFull())
                .collect(Collectors.toList());
    }
}