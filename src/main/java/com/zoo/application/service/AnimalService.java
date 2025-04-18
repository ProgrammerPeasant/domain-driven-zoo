package com.zoo.application.service;

import com.zoo.application.dto.AnimalDTO;
import com.zoo.application.mapper.AnimalMapper;
import com.zoo.domain.model.Animal;
import com.zoo.domain.repository.AnimalRepository;
import com.zoo.domain.valueobject.Food;
import com.zoo.domain.valueobject.Gender;
import com.zoo.presentation.api.CreateAnimalRequest;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    public AnimalService(AnimalRepository animalRepository, AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
    }

    public AnimalDTO createAnimal(CreateAnimalRequest request) {
        Food favoriteFood = new Food(request.getFavoriteFoodName(),
                Food.FoodType.valueOf(request.getFavoriteFoodType()));
        Animal animal = new Animal(
                request.getSpecies(),
                request.getName(),
                request.getBirthDate(),
                Gender.valueOf(request.getGender()),
                favoriteFood
        );

        Animal savedAnimal = animalRepository.save(animal);

        return animalMapper.toDto(savedAnimal);
    }
}
