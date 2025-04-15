package com.zoo.presentation.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateEnclosureRequest {
    @NotNull(message = "Type is required")
    private String type;

    @Positive(message = "Size must be positive")
    private double size;

    @Positive(message = "Maximum capacity must be positive")
    private int maxCapacity;
}