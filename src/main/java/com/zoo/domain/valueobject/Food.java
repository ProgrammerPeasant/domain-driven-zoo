package com.zoo.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Food {
    private final String name;
    private final FoodType type;

    public Food(String name, FoodType type) {
        this.name = name;
        this.type = type;
    }

    public enum FoodType {
        MEAT, PLANT, FISH, INSECTS, MIXED
    }
}