package com.zoo.presentation.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateAnimalRequest {
    @NotBlank(message = "Species is required")
    private String species;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Favorite food name is required")
    private String favoriteFoodName;

    @NotBlank(message = "Favorite food type is required")
    private String favoriteFoodType;
}