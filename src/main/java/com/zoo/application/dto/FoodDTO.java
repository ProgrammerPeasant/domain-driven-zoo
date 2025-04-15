package com.zoo.application.dto;

import com.zoo.domain.valueobject.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {
    private String name;
    private Food.FoodType type;
}