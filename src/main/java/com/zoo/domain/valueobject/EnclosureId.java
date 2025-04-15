package com.zoo.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class EnclosureId {
    private final UUID value;

    public EnclosureId(UUID value) {
        this.value = value;
    }
}