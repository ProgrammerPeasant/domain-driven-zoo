package com.zoo.application.mapper;

import com.zoo.application.dto.AnimalDTO;
import com.zoo.application.dto.FoodDTO;
import com.zoo.domain.model.Animal;
import com.zoo.domain.model.Enclosure;
import com.zoo.domain.valueobject.Food;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnimalMapper {
    public AnimalDTO toDto(Animal animal) {
        AnimalDTO dto = new AnimalDTO();
        dto.setId(animal.getId().getValue());
        dto.setSpecies(animal.getSpecies());
        dto.setName(animal.getName());
        dto.setBirthDate(animal.getBirthDate());
        dto.setGender(animal.getGender());

        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setName(animal.getFavoriteFood().getName());
        foodDTO.setType(animal.getFavoriteFood().getType());
        dto.setFavoriteFood(foodDTO);

        dto.setHealthStatus(animal.getHealthStatus());

        Enclosure enclosure = animal.getEnclosure();
        if (enclosure != null) {
            dto.setEnclosureId(enclosure.getId().getValue());
            dto.setEnclosureName("Enclosure " + enclosure.getId().getValue().toString().substring(0, 8));
        }

        return dto;
    }

    public List<AnimalDTO> toDtoList(List<Animal> animals) {
        return animals.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Food toFoodEntity(FoodDTO foodDTO) {
        return new Food(foodDTO.getName(), foodDTO.getType());
    }
}