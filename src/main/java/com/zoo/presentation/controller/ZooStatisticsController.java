package com.zoo.presentation.controller;

import com.zoo.application.dto.StatisticsDTO;
import com.zoo.application.service.ZooStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@Tag(name = "Zoo Statistics", description = "API для сбора статистики по зоопарку")
public class ZooStatisticsController {
    private final ZooStatisticsService zooStatisticsService;

    public ZooStatisticsController(ZooStatisticsService zooStatisticsService) {
        this.zooStatisticsService = zooStatisticsService;
    }

    @GetMapping
    @Operation(summary = "Получить полную статистику зоопарка")
    public ResponseEntity<StatisticsDTO> getZooStatistics() {
        StatisticsDTO statistics = zooStatisticsService.getFullStatistics();
        return ResponseEntity.ok(statistics);
    }
}