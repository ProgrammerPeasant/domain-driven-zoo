package com.zoo.domain.model;

import com.zoo.domain.valueobject.FeedingId;
import com.zoo.domain.valueobject.Food;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class FeedingSchedule {
    private final FeedingId id;
    private final Animal animal;
    private LocalDateTime feedingTime;
    private Food foodType;
    private boolean completed;

    public FeedingSchedule(Animal animal, LocalDateTime feedingTime, Food foodType) {
        this.id = new FeedingId(UUID.randomUUID());
        this.animal = animal;
        this.feedingTime = feedingTime;
        this.foodType = foodType;
        this.completed = false;
    }

    public void updateSchedule(LocalDateTime newFeedingTime, Food newFoodType) {
        this.feedingTime = newFeedingTime;
        this.foodType = newFoodType;
    }

    public void markAsCompleted() { // какое-то примерное представление завепшенного процесса кормления
        this.completed = true;
        animal.feed(foodType);
    }

    public boolean isUpcoming() {
        return !completed && feedingTime.isAfter(LocalDateTime.now());
    }

    public boolean isDue() {
        LocalDateTime now = LocalDateTime.now();
        return !completed &&
                feedingTime.isBefore(now) &&
                feedingTime.plusHours(1).isAfter(now); // каки-нибудь сроки кормления, я взял час к примеру
    }
}