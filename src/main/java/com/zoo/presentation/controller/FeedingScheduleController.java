package com.zoo.presentation.controller;

import com.zoo.application.dto.FeedingScheduleDTO;
import com.zoo.application.mapper.FeedingScheduleMapper;
import com.zoo.application.service.FeedingOrganizationService;
import com.zoo.domain.valueobject.Food;
import com.zoo.presentation.api.CreateFeedingScheduleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/feeding-schedules")
@Tag(name = "Feeding Schedules", description = "API для работы с расписаниями кормления")
public class FeedingScheduleController {
    private final FeedingOrganizationService feedingOrganizationService;
    private final FeedingScheduleMapper feedingScheduleMapper;

    public FeedingScheduleController(FeedingOrganizationService feedingOrganizationService, FeedingScheduleMapper feedingScheduleMapper) {
        this.feedingOrganizationService = feedingOrganizationService;
        this.feedingScheduleMapper = feedingScheduleMapper;
    }

    @GetMapping
    @Operation(summary = "Получить все расписания кормления")
    public ResponseEntity<List<FeedingScheduleDTO>> getAllFeedingSchedules() {
        return ResponseEntity.ok(feedingScheduleMapper.toDtoList(feedingOrganizationService.getAllUpcomingFeedings()));
    }

    @PostMapping
    @Operation(summary = "Создать новое расписание кормления")
    public ResponseEntity<FeedingScheduleDTO> createFeedingSchedule(@RequestBody CreateFeedingScheduleRequest request) {
        Food food = new Food(request.getFoodName(), Food.FoodType.valueOf(request.getFoodType()));
        FeedingScheduleDTO schedule = feedingScheduleMapper.toDto(
                feedingOrganizationService.scheduleFeeding(request.getAnimalId(), request.getFeedingTime(), food)
        );
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "Отметить кормление как завершенное")
    public ResponseEntity<Void> completeFeeding(@PathVariable UUID id) {
        boolean success = feedingOrganizationService.completeFeedingTask(id);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}