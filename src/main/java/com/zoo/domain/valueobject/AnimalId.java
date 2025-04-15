package com.zoo.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class AnimalId {
    private final UUID value;

    public AnimalId(UUID value) {
        this.value = value;
    }
}