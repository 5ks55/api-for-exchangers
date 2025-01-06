/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.controller;

/**
 *
 * @author Nkt, Nazar
 */

import com.example.currencytracker.dto.ExchangePlatformDto;
import com.example.currencytracker.model.ExchangePlatform;
import com.example.currencytracker.service.ExchangePlatformService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/api/exchange-platforms")
@Tag(name = "Exchange Platform Controller", description = "API do zarządzania platformami wymiany")
public class ExchangePlatformController {

    private static final Logger logger = LogManager.getLogger(ExchangePlatformController.class);

    @Autowired
    private ExchangePlatformService exchangePlatformService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie platformy wymiany", description = "Pobiera wszystkie platformy wymiany z FastAPI")
    @ApiResponse(responseCode = "200", description = "Pomyślnie pobrano wszystkie platformy wymiany")
    public ResponseEntity<List<ExchangePlatform>> getExchangePlatformsFromPython(
            @Parameter(description = "Środowisko: 'dev' lub 'prod'")
            @RequestParam(defaultValue = "dev") String environment) {
        logger.info("Rozpoczynanie pobierania platform wymiany z FastAPI.");

        String fastApiUrl = "http://localhost:8000/api/exchange-platforms?environment=" + environment;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ExchangePlatform[] platformsArray = restTemplate.getForObject(fastApiUrl, ExchangePlatform[].class);
            List<ExchangePlatform> platforms = Arrays.asList(platformsArray);

            logger.info("Pomyślnie pobrano {} platform wymiany z FastAPI.", platforms.size());
            return ResponseEntity.ok(platforms);
        } catch (Exception e) {
            logger.error("Błąd podczas pobierania platform wymiany z FastAPI: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{id}")
    @Operation(summary = "Pobierz platformę wymiany po ID", description = "Pobiera platformę wymiany po jej ID")
    @ApiResponse(responseCode = "200", description = "Pomyślnie pobrano platformę wymiany")
    @ApiResponse(responseCode = "404", description = "Platforma wymiany nie znaleziona")
    public ExchangePlatformDto getExchangePlatformById(@Parameter(description = "ID platformy wymiany do pobrania") @PathVariable String id) {
        logger.info("Otrzymano żądanie pobrania platformy wymiany o ID: {}", id);
        ExchangePlatform platform = exchangePlatformService.getExchangePlatformById(id);
        if (platform != null) {
            logger.debug("Zwrócono platformę wymiany: {}", platform);
            return new ExchangePlatformDto(platform.getId(), platform.getName(), platform.getParseUrl());
        } else {
            logger.warn("Nie znaleziono platformy wymiany o ID: {}", id);
            return null; 
        }
    }

    @PostMapping
    @Operation(summary = "Dodaj nową platformę wymiany", description = "Tworzy nową platformę wymiany")
    @ApiResponse(responseCode = "201", description = "Pomyślnie dodano nową platformę wymiany")
    public ExchangePlatformDto addExchangePlatform(@RequestBody ExchangePlatform platform) {
        logger.info("Otrzymano żądanie dodania nowej platformy wymiany: {}", platform);
        ExchangePlatform newPlatform = exchangePlatformService.addExchangePlatform(platform);
        logger.info("Nowa platforma wymiany została dodana z ID: {}", newPlatform.getId());
        return new ExchangePlatformDto(newPlatform.getId(), newPlatform.getName(), newPlatform.getParseUrl());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aktualizuj platformę wymiany", description = "Aktualizuje istniejącą platformę wymiany")
    @ApiResponse(responseCode = "200", description = "Pomyślnie zaktualizowano platformę wymiany")
    @ApiResponse(responseCode = "404", description = "Platforma wymiany nie znaleziona")
    public ExchangePlatformDto updateExchangePlatform(
            @Parameter(description = "ID platformy wymiany do zaktualizowania") @PathVariable String id,
            @RequestBody ExchangePlatform platform) {
        logger.info("Otrzymano żądanie aktualizacji platformy wymiany o ID: {}", id);
        ExchangePlatform updatedPlatform = exchangePlatformService.updateExchangePlatform(id, platform);
        if (updatedPlatform != null) {
            logger.info("Platforma wymiany o ID {} została pomyślnie zaktualizowana.", id);
            return new ExchangePlatformDto(updatedPlatform.getId(), updatedPlatform.getName(), updatedPlatform.getParseUrl());
        } else {
            logger.warn("Nie znaleziono platformy wymiany o ID: {}", id);
            return null; 
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń platformę wymiany", description = "Usuwa platformę wymiany po jej ID")
    @ApiResponse(responseCode = "200", description = "Pomyślnie usunięto platformę wymiany")
    @ApiResponse(responseCode = "404", description = "Platforma wymiany nie znaleziona")
    public String deleteExchangePlatform(@Parameter(description = "ID platformy wymiany do usunięcia") @PathVariable String id) {
        logger.info("Otrzymano żądanie usunięcia platformy wymiany o ID: {}", id);
        try {
            exchangePlatformService.deleteExchangePlatform(id);
            logger.info("Platforma wymiany o ID {} została pomyślnie usunięta.", id);
            return "Platforma wymiany o ID " + id + " została usunięta.";
        } catch (Exception e) {
            logger.error("Wystąpił błąd podczas usuwania platformy wymiany o ID: {}", id, e);
            return "Nie udało się usunąć platformy wymiany.";
        }
    }
}
