package com.zoo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedingScheduleDTO {
    private UUID id;
    private UUID animalId;
    private String animalName;
    private LocalDateTime feedingTime;
    private FoodDTO foodType;
    private boolean completed;
}