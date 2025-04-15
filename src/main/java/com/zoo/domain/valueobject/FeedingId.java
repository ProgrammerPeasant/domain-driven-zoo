package com.zoo.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class FeedingId {
    private final UUID value;

    public FeedingId(UUID value) {
        this.value = value;
    }
}