package com.zoo.application.service;

import com.zoo.domain.event.FeedingTimeEvent;
import com.zoo.domain.model.Animal;
import com.zoo.domain.model.FeedingSchedule;
import com.zoo.domain.repository.AnimalRepository;
import com.zoo.domain.repository.FeedingScheduleRepository;
import com.zoo.domain.valueobject.AnimalId;
import com.zoo.domain.valueobject.FeedingId;
import com.zoo.domain.valueobject.Food;
import com.zoo.infrastructure.eventbus.EventBus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedingOrganizationService {
    private final FeedingScheduleRepository feedingScheduleRepository;
    private final AnimalRepository animalRepository;
    private final EventBus eventBus;

    public FeedingOrganizationService(
            FeedingScheduleRepository feedingScheduleRepository,
            AnimalRepository animalRepository,
            EventBus eventBus
    ) {
        this.feedingScheduleRepository = feedingScheduleRepository;
        this.animalRepository = animalRepository;
        this.eventBus = eventBus;
    }

    public FeedingSchedule scheduleFeeding(UUID animalId, LocalDateTime feedingTime, Food foodType) {
        Optional<Animal> animalOpt = animalRepository.findById(new AnimalId(animalId));

        if (animalOpt.isEmpty()) {
            throw new IllegalArgumentException("Animal not found");
        }

        Animal animal = animalOpt.get();
        FeedingSchedule schedule = new FeedingSchedule(animal, feedingTime, foodType);
        return feedingScheduleRepository.save(schedule);
    }

    public boolean completeFeedingTask(UUID feedingId) {
        Optional<FeedingSchedule> scheduleOpt = feedingScheduleRepository.findById(new FeedingId(feedingId));

        if (scheduleOpt.isEmpty()) {
            return false;
        }

        FeedingSchedule schedule = scheduleOpt.get();
        schedule.markAsCompleted();
        feedingScheduleRepository.save(schedule);
        return true;
    }

    public List<FeedingSchedule> getScheduleForAnimal(UUID animalId) {
        return feedingScheduleRepository.findByAnimalId(animalId);
    }

    public List<FeedingSchedule> getAllUpcomingFeedings() {
        LocalDateTime now = LocalDateTime.now();
        return feedingScheduleRepository.findDueFeedings(now, now.plusDays(1));
    }

    @Scheduled(fixedRate = 60000) // Проверка каждую минуту
    public void checkDueFeedings() {
        LocalDateTime now = LocalDateTime.now();
        List<FeedingSchedule> dueFeedings = feedingScheduleRepository.findDueFeedings(
                now.minusMinutes(5),
                now.plusMinutes(5)
        );

        if (!dueFeedings.isEmpty()) {
            eventBus.publish(new FeedingTimeEvent(dueFeedings));
        }
    }
}