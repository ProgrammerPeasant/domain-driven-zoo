package com.zoo.presentation.controller;

import com.zoo.application.dto.AnimalDTO;
import com.zoo.application.mapper.AnimalMapper;
import com.zoo.application.service.AnimalService;
import com.zoo.domain.model.Animal;
import com.zoo.domain.repository.AnimalRepository;
import com.zoo.domain.valueobject.AnimalId;
import com.zoo.presentation.api.CreateAnimalRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/animals")
@Tag(name = "Animals", description = "API для работы с животными зоопарка")
public class AnimalController {
    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final AnimalService animalService;

    public AnimalController(AnimalRepository animalRepository, AnimalMapper animalMapper, AnimalService animalService) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
        this.animalService = animalService;
    }

    @GetMapping
    @Operation(summary = "Получить список всех животных")
    public ResponseEntity<List<AnimalDTO>> getAllAnimals() {
        List<Animal> animals = animalRepository.findAll();
        return ResponseEntity.ok(animalMapper.toDtoList(animals));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию о животном по ID")
    public ResponseEntity<AnimalDTO> getAnimal(@PathVariable UUID id) {
        Optional<Animal> animalOpt = animalRepository.findById(new AnimalId(id));
        return animalOpt
                .map(animal -> ResponseEntity.ok(animalMapper.toDto(animal)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Добавить новое животное")
    public ResponseEntity<AnimalDTO> createAnimal(@RequestBody CreateAnimalRequest request) {
        AnimalDTO animalDto = animalService.createAnimal(request);
        return new ResponseEntity<>(animalDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить животное")
    public ResponseEntity<Void> deleteAnimal(@PathVariable UUID id) {
        Optional<Animal> animalOpt = animalRepository.findById(new AnimalId(id));
        if (animalOpt.isPresent()) {
            Animal animal = animalOpt.get();
            animal.removeFromEnclosure();
            animalRepository.delete(animal);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/heal")
    @Operation(summary = "Вылечить животное")
    public ResponseEntity<AnimalDTO> healAnimal(@PathVariable UUID id) {
        Optional<Animal> animalOpt = animalRepository.findById(new AnimalId(id));
        if (animalOpt.isPresent()) {
            Animal animal = animalOpt.get();
            animal.heal();
            animalRepository.save(animal);
            return ResponseEntity.ok(animalMapper.toDto(animal));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/makeSick")
    @Operation(summary = "Отметить животное как больное")
    public ResponseEntity<AnimalDTO> makeSick(@PathVariable UUID id) {
        Optional<Animal> animalOpt = animalRepository.findById(new AnimalId(id));
        if (animalOpt.isPresent()) {
            Animal animal = animalOpt.get();
            animal.makeIll();
            animalRepository.save(animal);
            return ResponseEntity.ok(animalMapper.toDto(animal));
        }
        return ResponseEntity.notFound().build();
    }
}