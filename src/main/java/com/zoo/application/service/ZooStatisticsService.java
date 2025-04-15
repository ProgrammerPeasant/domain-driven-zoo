package com.zoo.application.service;

import com.zoo.domain.model.Animal;
import com.zoo.domain.model.Enclosure;
import com.zoo.domain.repository.AnimalRepository;
import com.zoo.domain.repository.EnclosureRepository;
import com.zoo.domain.valueobject.EnclosureType;
import com.zoo.domain.valueobject.HealthStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZooStatisticsService {
    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;

    public ZooStatisticsService(
            AnimalRepository animalRepository,
            EnclosureRepository enclosureRepository
    ) {
        this.animalRepository = animalRepository;
        this.enclosureRepository = enclosureRepository;
    }

    public int getTotalAnimalCount() {
        return animalRepository.findAll().size();
    }

    public int getHealthyAnimalCount() {
        return (int) animalRepository.findAll().stream()
                .filter(animal -> animal.getHealthStatus() == HealthStatus.HEALTHY)
                .count();
    }

    public int getSickAnimalCount() {
        return (int) animalRepository.findAll().stream()
                .filter(animal -> animal.getHealthStatus() == HealthStatus.SICK)
                .count();
    }

    public Map<String, Integer> getAnimalCountBySpecies() {
        Map<String, Integer> result = new HashMap<>();
        List<Animal> animals = animalRepository.findAll();

        for (Animal animal : animals) {
            result.put(animal.getSpecies(), result.getOrDefault(animal.getSpecies(), 0) + 1);
        }

        return result;
    }

    public int getAvailableEnclosureCount() {
        return enclosureRepository.findAvailable().size();
    }

    public int getTotalEnclosureCount() {
        return enclosureRepository.findAll().size();
    }

    public Map<EnclosureType, Integer> getEnclosureCountByType() {
        Map<EnclosureType, Integer> result = new HashMap<>();
        List<Enclosure> enclosures = enclosureRepository.findAll();

        for (Enclosure enclosure : enclosures) {
            result.put(enclosure.getType(), result.getOrDefault(enclosure.getType(), 0) + 1);
        }

        return result;
    }

    public Map<String, Object> getFullStatistics() {
        Map<String, Object> statistics = new HashMap<>();

        statistics.put("totalAnimalCount", getTotalAnimalCount());
        statistics.put("healthyAnimalCount", getHealthyAnimalCount());
        statistics.put("sickAnimalCount", getSickAnimalCount());
        statistics.put("animalsBySpecies", getAnimalCountBySpecies());

        statistics.put("totalEnclosureCount", getTotalEnclosureCount());
        statistics.put("availableEnclosureCount", getAvailableEnclosureCount());
        statistics.put("enclosuresByType", getEnclosureCountByType());

        statistics.put("occupancyRate", calculateOccupancyRate());

        return statistics;
    }

    private double calculateOccupancyRate() {
        List<Enclosure> enclosures = enclosureRepository.findAll();
        if (enclosures.isEmpty()) {
            return 0.0;
        }

        int totalCapacity = enclosures.stream().mapToInt(Enclosure::getMaxCapacity).sum();
        int totalOccupied = enclosures.stream().mapToInt(Enclosure::getCurrentAnimalCount).sum();

        return (double) totalOccupied / totalCapacity;
    }
}