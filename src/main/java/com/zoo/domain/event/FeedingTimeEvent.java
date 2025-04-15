package com.zoo.domain.event;

import com.zoo.domain.model.FeedingSchedule;
import lombok.Getter;

import java.util.List;

@Getter
public class FeedingTimeEvent extends DomainEvent {
    private final List<FeedingSchedule> dueFeedings;

    public FeedingTimeEvent(List<FeedingSchedule> dueFeedings) {
        super();
        this.dueFeedings = dueFeedings;
    }
}