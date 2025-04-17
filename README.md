# Zoo Management System

---
Проект по управлению зоопарком, реализованный с использованием концепций Domain-Driven Design и принципов Clean Architecture. Система позволяет управлять животными, вольерами, расписанием кормления и получать статистику зоопарка.

БД реализована как in-memory.
## Реализованный функционал

### 1. Управление животными
- **Получение списка всех животных:**
    - Класс: `AnimalController`, метод `getAllAnimals`.
- **Получение информации о конкретном животном по ID:**
    - Класс: `AnimalController`, метод `getAnimal`.
- **Добавление нового животного:**
    - Класс: `AnimalController`, метод `createAnimal`.
- **Удаление животного:**
    - Класс: `AnimalController`, метод `deleteAnimal`.
- **Лечение и пометка животного как больного:**
    - Класс: `AnimalController`, методы `healAnimal` и `makeSick`.

### 2. Управление вольерами
- **Получение списка всех вольеров:**
    - Класс: `EnclosureController`, метод `getAllEnclosures`.
- **Создание нового вольера:**
    - Класс: `EnclosureController`, метод `createEnclosure`.
- **Удаление вольера:**
    - Класс: `EnclosureController`, метод `deleteEnclosure`.
- **Добавление и удаление животного из вольера:**
    - Класс: `EnclosureController`, методы `addAnimalToEnclosure` и `removeAnimalFromEnclosure`.
- **Уборка вольера:**
    - Класс: `EnclosureController`, метод `cleanEnclosure`.

### 3. Расписание кормления
- **Получение расписания кормления:**
    - Класс: `FeedingScheduleController`, метод `getAllFeedingSchedules`.
- **Создание расписания кормления:**
    - Класс: `FeedingScheduleController`, метод `createFeedingSchedule`.
- **Завершение расписания кормления:**
    - Класс: `FeedingScheduleController`, метод `completeFeeding`.

### 4. Статистика зоопарка
- **Получение общей статистики зоопарка:**
    - Класс: `ZooStatisticsController`, метод `getZooStatistics`.

---

## Применение концепций Domain-Driven Design и принципов Clean Architecture

### Domain-Driven Design (DDD)
1. **Value Objects (Объекты-значения):**
    - `AnimalId`: используется для идентификации животных.
    - `EnclosureId`: используется для идентификации вольеров.
    - `FeedingId`: используется для идентификации расписаний кормления.
    - `Food`: объект-значение, отражающий тип и название корма. 
    - **Местоположение:** Пакет `com.zoo.domain.valueobject`.
    - Также различные enumы, такие как `EnclosureType`, `FoodType`, `Gender` и т.д., используются для определения различных типов и категорий.

2. **Entities (Сущности):**
    - `Animal`: сущность, представляющая животного.
    - `Enclosure`: сущность, представляющая вольер.
    - `FeedingSchedule`: сущность, представляющая расписание кормления.
    - **Местоположение:** Пакет `com.zoo.domain.model`.

3. **Aggregates (Агрегаты):**
    - `Animal` и `Enclosure` являются корневыми агрегатами.

4. **Repositories (Репозитории):**
    - `AnimalRepository`: интерфейс для управления животными.
    - `EnclosureRepository`: интерфейс для управления вольерами.
    - **Местоположение:** Пакет `com.zoo.domain.repository`.
    - Реализация репозиториев находится в пакете `com.zoo.infrastructure.repository`.

5. **Domain Services (Сервисы домена):**
    - `FeedingOrganizationService`: управляет логикой кормления животных.
    - `AnimalTransferService`: управляет логикой перемещения животных между вольерами.
    - `ZooStatisticsService`: управляет логикой получения статистики зоопарка.
    - **Местоположение:** Пакет `com.zoo.application.service`.

### Clean Architecture
1. **Разделение слоев:**
    - **Domain (Домен):** включает в себя сущности, объекты-значения и репозитории.
    - **Application (Приложение):** включает в себя DTO, сервисы и мапперы.
    - **Presentation (Представление):** контроллеры, работающие с REST API.

2. **DTO (Data Transfer Object):**
    - Используются для передачи данных между слоями.
    - **Пример:** `AnimalDTO`, `EnclosureDTO`.
    - **Местоположение:** Пакет `com.zoo.application.dto`.

3. **Mapper (Мапперы):**
    - Мапперы преобразуют сущности домена в DTO и обратно.
    - **Пример:** `AnimalMapper`, `EnclosureMapper`.
    - **Местоположение:** Пакет `com.zoo.application.mapper`.

4. **Controller (Контроллеры):**
    - Контроллеры реализуют взаимодействие с пользователем через REST API.
    - **Пример:** `AnimalController`, `EnclosureController`.
    - **Местоположение:** Пакет `com.zoo.presentation.controller`.

---

## Перечень enum

Для удобства проверки функционала в проекте реализованы следующие перечисления:

### 1. **Типы животных (EnclosureType):**
```java
case PREDATOR -> animal.getSpecies().contains("Tiger") ||
                    animal.getSpecies().contains("Lion") ||
                    animal.getSpecies().contains("Wolf");
case HERBIVORE -> animal.getSpecies().contains("Deer") ||
                    animal.getSpecies().contains("Elephant") ||
                    animal.getSpecies().contains("Giraffe");
case BIRD -> animal.getSpecies().contains("Eagle") ||
                    animal.getSpecies().contains("Parrot") ||
                    animal.getSpecies().contains("Penguin");
case AQUARIUM -> animal.getSpecies().contains("Fish") ||
                    animal.getSpecies().contains("Shark") ||
                    animal.getSpecies().contains("Dolphin");
```

### 2. **Типы корма:**
```java
public enum FoodType {
    MEAT, PLANT, FISH, INSECTS, MIXED
}
```

### 3. **Пол животного:**
```java
public enum Gender {
    MALE, FEMALE, UNKNOWN
}
```

---

## Установка и запуск

1. Склонируйте репозиторий:
   ```bash
   git clone https://github.com/ProgrammerPeasant/domain-driven-zoo.git
   cd domain-driven-zoo
   ```

2. Установите зависимости:
   ```bash
   ./gradlew build
   ```

3. Запустите приложение:
   ```bash
   ./gradlew bootRun
   ```

4. Swagger UI для тестирования API:
   ```
   http://localhost:8080/swagger-ui.html
   ```

---
