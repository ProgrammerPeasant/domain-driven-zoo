package com.zoo.presentation.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreateFeedingScheduleRequest {
    @NotNull(message = "Animal ID is required")
    private UUID animalId;

    @NotNull(message = "Feeding time is required")
    private LocalDateTime feedingTime;

    @NotBlank(message = "Food name is required")
    private String foodName;

    @NotBlank(message = "Food type is required")
    private String foodType;
}