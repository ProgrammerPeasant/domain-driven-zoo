package com.zoo.application.dto;

import com.zoo.domain.valueobject.EnclosureType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnclosureDTO {
    private UUID id;
    private EnclosureType type;
    private double size;
    private int maxCapacity;
    private int currentAnimalCount;
    private List<UUID> animalIds;
    private boolean cleaningNeeded;
}