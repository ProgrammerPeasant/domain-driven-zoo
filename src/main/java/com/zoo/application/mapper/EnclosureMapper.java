package com.zoo.application.mapper;

import com.zoo.application.dto.EnclosureDTO;
import com.zoo.domain.model.Animal;
import com.zoo.domain.model.Enclosure;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class EnclosureMapper {
    public EnclosureDTO toDto(Enclosure enclosure) {
        EnclosureDTO dto = new EnclosureDTO();
        dto.setId(enclosure.getId().getValue());
        dto.setType(enclosure.getType());
        dto.setSize(enclosure.getSize());
        dto.setMaxCapacity(enclosure.getMaxCapacity());
        dto.setCurrentAnimalCount(enclosure.getCurrentAnimalCount());
        dto.setCleaningNeeded(enclosure.isCleaningNeeded());

        List<Animal> animals = enclosure.getAnimals();
        List<UUID> animalIds = animals.stream()
                .map(animal -> animal.getId().getValue())
                .collect(Collectors.toList());
        dto.setAnimalIds(animalIds);

        return dto;
    }

    public List<EnclosureDTO> toDtoList(List<Enclosure> enclosures) {
        return enclosures.stream().map(this::toDto).collect(Collectors.toList());
    }
}