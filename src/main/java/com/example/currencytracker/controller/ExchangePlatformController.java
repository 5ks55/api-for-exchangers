/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.controller;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.dto.ExchangePlatformDto;
import com.example.currencytracker.model.ExchangePlatform;
import com.example.currencytracker.service.ExchangePlatformService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exchange-platforms")
public class ExchangePlatformController {

    private static final Logger logger = LogManager.getLogger(ExchangePlatformController.class);

    @Autowired
    private ExchangePlatformService exchangePlatformService;

    // Pobierz wszystkie platformy wymiany
    @GetMapping
    public List<ExchangePlatformDto> getAllExchangePlatforms() {
        logger.info("Otrzymano żądanie pobrania wszystkich platform wymiany.");
        List<ExchangePlatform> platforms = exchangePlatformService.getAllExchangePlatforms();
        List<ExchangePlatformDto> platformDtos = platforms.stream()
            .map(platform -> new ExchangePlatformDto(platform.getId(), platform.getName(), platform.getParseUrl()))
            .collect(Collectors.toList());
        logger.debug("Zwrócono {} platformy wymiany.", platformDtos.size());
        return platformDtos;
    }

    // Pobierz platformę wymiany po ID
    @GetMapping("/{id}")
    public ExchangePlatformDto getExchangePlatformById(@PathVariable String id) {
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

    // Dodaj nową platformę wymiany
    @PostMapping
    public ExchangePlatformDto addExchangePlatform(@RequestBody ExchangePlatform platform) {
        logger.info("Otrzymano żądanie dodania nowej platformy wymiany: {}", platform);
        ExchangePlatform newPlatform = exchangePlatformService.addExchangePlatform(platform);
        logger.info("Nowa platforma wymiany została dodana z ID: {}", newPlatform.getId());
        return new ExchangePlatformDto(newPlatform.getId(), newPlatform.getName(), newPlatform.getParseUrl());
    }
    
    // Zaktualizuj dane platformy wymiany
    @PutMapping("/{id}")
    public ExchangePlatformDto updateExchangePlatform(@PathVariable String id, @RequestBody ExchangePlatform platform) {
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
    
    // Usuń platformę wymiany
    @DeleteMapping("/{id}")
    public String deleteExchangePlatform(@PathVariable String id) {
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
