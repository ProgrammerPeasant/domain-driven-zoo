package com.zoo.presentation.controller;

import com.zoo.application.dto.EnclosureDTO;
import com.zoo.application.mapper.EnclosureMapper;
import com.zoo.domain.model.Enclosure;
import com.zoo.domain.repository.EnclosureRepository;
import com.zoo.domain.valueobject.EnclosureId;
import com.zoo.domain.valueobject.EnclosureType;
import com.zoo.presentation.api.CreateEnclosureRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/enclosures")
@Tag(name = "Enclosures", description = "API для работы с вольерами зоопарка")
public class EnclosureController {
    private final EnclosureRepository enclosureRepository;
    private final EnclosureMapper enclosureMapper;

    public EnclosureController(EnclosureRepository enclosureRepository, EnclosureMapper enclosureMapper) {
        this.enclosureRepository = enclosureRepository;
        this.enclosureMapper = enclosureMapper;
    }

    @GetMapping
    @Operation(summary = "Получить список всех вольеров")
    public ResponseEntity<List<EnclosureDTO>> getAllEnclosures() {
        List<Enclosure> enclosures = enclosureRepository.findAll();
        return ResponseEntity.ok(enclosureMapper.toDtoList(enclosures));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию о вольере по ID")
    public ResponseEntity<EnclosureDTO> getEnclosure(@PathVariable UUID id) {
        Optional<Enclosure> enclosureOpt = enclosureRepository.findById(new EnclosureId(id));
        return enclosureOpt
                .map(enclosure -> ResponseEntity.ok(enclosureMapper.toDto(enclosure)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/available")
    @Operation(summary = "Получить список доступных вольеров")
    public ResponseEntity<List<EnclosureDTO>> getAvailableEnclosures() {
        List<Enclosure> enclosures = enclosureRepository.findAvailable();
        return ResponseEntity.ok(enclosureMapper.toDtoList(enclosures));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Получить вольеры по типу")
    public ResponseEntity<List<EnclosureDTO>> getEnclosuresByType(@PathVariable String type) {
        try {
            EnclosureType enclosureType = EnclosureType.valueOf(type.toUpperCase());
            List<Enclosure> enclosures = enclosureRepository.findByType(enclosureType);
            return ResponseEntity.ok(enclosureMapper.toDtoList(enclosures));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @Operation(summary = "Создать новый вольер")
    public ResponseEntity<EnclosureDTO> createEnclosure(@RequestBody CreateEnclosureRequest request) {
        try {
            EnclosureType type = EnclosureType.valueOf(request.getType());
            Enclosure enclosure = new Enclosure(type, request.getSize(), request.getMaxCapacity());
            Enclosure savedEnclosure = enclosureRepository.save(enclosure);
            return new ResponseEntity<>(enclosureMapper.toDto(savedEnclosure), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить вольер")
    public ResponseEntity<Void> deleteEnclosure(@PathVariable UUID id) {
        Optional<Enclosure> enclosureOpt = enclosureRepository.findById(new EnclosureId(id));
        if (enclosureOpt.isPresent()) {
            Enclosure enclosure = enclosureOpt.get();

            // Проверка, что в вольере нет животных
            if (!enclosure.getAnimals().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            enclosureRepository.delete(enclosure);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/clean")
    @Operation(summary = "Произвести уборку в вольере")
    public ResponseEntity<EnclosureDTO> cleanEnclosure(@PathVariable UUID id) {
        Optional<Enclosure> enclosureOpt = enclosureRepository.findById(new EnclosureId(id));
        if (enclosureOpt.isPresent()) {
            Enclosure enclosure = enclosureOpt.get();
            enclosure.clean();
            enclosureRepository.save(enclosure);
            return ResponseEntity.ok(enclosureMapper.toDto(enclosure));
        }
        return ResponseEntity.notFound().buil