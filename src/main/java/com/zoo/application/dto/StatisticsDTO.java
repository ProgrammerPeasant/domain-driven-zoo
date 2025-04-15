package com.zoo.application.dto;

import com.zoo.domain.valueobject.EnclosureType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDTO {
    private int totalAnimalCount;
    private int healthyAnimalCount;
    private int sickAnimalCount;
    private Map<String, Integer> animalsBySpecies;

    private int totalEnclosureCount;
    private int availableEnclosureCount;
    private Map<EnclosureType, Integer> enclosuresByType;

    private double occupancyRate;
}