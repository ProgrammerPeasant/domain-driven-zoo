package com.zoo.application.mapper;

import com.zoo.application.dto.FeedingScheduleDTO;
import com.zoo.application.dto.FoodDTO;
import com.zoo.domain.model.FeedingSchedule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeedingScheduleMapper {
    public FeedingScheduleDTO toDto(FeedingSchedule schedule) {
        FeedingScheduleDTO dto = new FeedingScheduleDTO();
        dto.setId(schedule.getId().getValue());
        dto.setAnimalId(schedule.getAnimal().getId().getValue());
        dto.setAnimalName(schedule.getAnimal().getName());
        dto.setFeedingTime(schedule.getFeedingTime());

        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setName(schedule.getFoodType().getName());
        foodDTO.setType(schedule.getFoodType().getType());
        dto.setFoodType(foodDTO);

        dto.setCompleted(schedule.isCompleted());

        return dto;
    }

    public List<FeedingScheduleDTO> toDtoList(List<FeedingSchedule> schedules) {
        return schedules.stream().map(this::toDto).collect(Collectors.toList());
    }
}