package com.zoo.domain.model;

import com.zoo.domain.valueobject.AnimalId;
import com.zoo.domain.valueobject.Food;
import com.zoo.domain.valueobject.Gender;
import com.zoo.domain.valueobject.HealthStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Animal {
    private final AnimalId id;
    private String species;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private Food favoriteFood;
    private HealthStatus healthStatus;
    private Enclosure enclosure;

    public Animal(String species, String name, LocalDate birthDate,
                  Gender gender, Food favoriteFood) {
        this.id = new AnimalId(UUID.randomUUID());
        this.species = species;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.favoriteFood = favoriteFood;
        this.healthStatus = HealthStatus.HEALTHY;
    }

    public void feed(Food food) {
        if (favoriteFood.equals(food)) {
            // здесь пусто, но вообще можно расширить логику, допустим какой-то уровень настроения, просто для
            // дальнейшей логики приложения особо смысла не имеет
        }
    }

    public void heal() {
        if (healthStatus == HealthStatus.SICK) {
            healthStatus = HealthStatus.HEALTHY;
        }
    }

    public void makeIll() {
        if (healthStatus == HealthStatus.HEALTHY) {
            healthStatus = HealthStatus.SICK;
        }
    }

    public boolean moveToEnclosure(Enclosure newEnclosure) {
        if (enclosure != null) {
            enclosure.removeAnimal(this);
        }

        boolean added = newEnclosure.addAnimal(this);
        if (added) {
            this.enclosure = newEnclosure;
            return true;
        }

        if (enclosure != null) {
            enclosure.addAnimal(this);
        }
        return false;
    }

    public void removeFromEnclosure() {
        if (enclosure != null) {
            enclosure.removeAnimal(this);
            enclosure = null;
        }
    }
}
