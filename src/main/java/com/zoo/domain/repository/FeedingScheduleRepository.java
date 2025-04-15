package com.zoo.domain.repository;

import com.zoo.domain.model.Animal;
import com.zoo.domain.model.FeedingSchedule;
import com.zoo.domain.valueobject.FeedingId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeedingScheduleRepository {
    FeedingSchedule save(FeedingSchedule feedingSchedule);
    Optional<FeedingSchedule> findById(FeedingId id);
    List<FeedingSchedule> findAll();
    void delete(FeedingSchedule feedingSchedule);
    List<FeedingSchedule> findByAnimal(Animal animal);
    List<FeedingSchedule> findDueFeedings(LocalDateTime from, LocalDateTime to);
    List<FeedingSchedule> findByAnimalId(UUID animalId);
}