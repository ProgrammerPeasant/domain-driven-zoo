package com.zoo.application.dto;

import com.zoo.domain.valueobject.Gender;
import com.zoo.domain.valueobject.HealthStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {
    private UUID id;
    private String species;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private FoodDTO favoriteFood;
    private HealthStatus healthStatus;
    private UUID enclosureId;
    private String enclosureName;
}