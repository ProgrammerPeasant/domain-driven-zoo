package com.zoo.infrastructure.repository;

import com.zoo.domain.model.Animal;
import com.zoo.domain.model.FeedingSchedule;
import com.zoo.domain.repository.FeedingScheduleRepository;
import com.zoo.domain.valueobject.FeedingId;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryFeedingScheduleRepository implements FeedingScheduleRepository {
    private final Map<UUID, FeedingSchedule> schedules = new HashMap<>();

    @Override
    public FeedingSchedule save(FeedingSchedule feedingSchedule) {
        schedules.put(feedingSchedule.getId().getValue(), feedingSchedule);
        return feedingSchedule;
    }

    @Override
    public Optional<FeedingSchedule> findById(FeedingId id) {
        return Optional.ofNullable(schedules.get(id.getValue()));
    }

    @Override
    public List<FeedingSchedule> findAll() {
        return new ArrayList<>(schedules.values());
    }

    @Override
    public void delete(FeedingSchedule feedingSchedule) {
        schedules.remove(feedingSchedule.getId().getValue());
    }

    @Override
    public List<FeedingSchedule> findByAnimal(Animal animal) {
        return schedules.values().stream()
                .filter(schedule -> schedule.getAnimal().equals(animal))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedingSchedule> findDueFeedings(LocalDateTime from, LocalDateTime to) {
        return schedules.values().stream()
                .filter(schedule -> !schedule.isCompleted() &&
                        schedule.getFeedingTime().isAfter(from) &&
                        schedule.getFeedingTime().isBefore(to))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedingSchedule> findByAnimalId(UUID animalId) {
        return schedules.values().stream()
                .filter(schedule -> schedule.getAnimal().getId().getValue().equals(animalId))
                .collect(Collectors.toList());
    }
}