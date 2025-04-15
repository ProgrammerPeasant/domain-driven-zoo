package com.zoo.domain.repository;

import com.zoo.domain.model.Enclosure;
import com.zoo.domain.valueobject.EnclosureId;
import com.zoo.domain.valueobject.EnclosureType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnclosureRepository {
    Enclosure save(Enclosure enclosure);
    Optional<Enclosure> findById(EnclosureId id);
    List<Enclosure> findAll();
    void delete(Enclosure enclosure);
    List<Enclosure> findByType(EnclosureType type);
    List<Enclosure> findAvailable();
}