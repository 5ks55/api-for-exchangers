/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.controller;

/**
 *
 * @author Nkt, Nazar, IVAN
 */

import com.example.currencytracker.dto.CurrencyPairUpdate;
import com.example.currencytracker.exception.EntityNotFoundException;
import com.example.currencytracker.dto.ExchangeRateDto;
import com.example.currencytracker.service.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;


import jakarta.validation.Valid;
import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/api/exchange-rates")
@Tag(name = "Exchange Rate Controller", description = "API do zarządzania kursami walut")
public class ExchangeRateController {
    
    private static final Logger logger = LogManager.getLogger(ExchangeRateController.class);

    @Autowired
    private ExchangeRateService exchangeRateService;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie kursy walut", description = "Pobiera wszystkie kursy walut z FastAPI")
    @ApiResponse(responseCode = "200", description = "Pomyślnie pobrano wszystkie kursy walut")
    public ResponseEntity<List<ExchangeRateDto>> getAllExchangeRates(
            @Parameter(description = "Środowisko: 'dev' lub 'prod'")
            @RequestParam(value = "environment", defaultValue = "dev") String environment) {
        logger.info("Rozpoczynanie pobierania wszystkich kursów walut z FastAPI.");

        String fastApiUrl = "http://localhost:8000/api/exchange-rates?environment=" + environment;

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ExchangeRateDto[]> response = restTemplate.getForEntity(fastApiUrl, ExchangeRateDto[].class);

            List<ExchangeRateDto> rates = Arrays.asList(response.getBody());
            logger.info("Pobrano {} kursów walut z FastAPI.", rates.size());

            return ResponseEntity.ok(rates);
        } catch (Exception e) {
            logger.error("Błąd podczas pobierania kursów walut z FastAPI: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz kurs waluty po ID", description = "Pobiera kurs waluty po jego ID")
    @ApiResponse(responseCode = "200", description = "Pomyślnie pobrano kurs waluty")
    @ApiResponse(responseCode = "404", description = "Kurs waluty nie znaleziony")
    public ResponseEntity<ExchangeRateDto> getExchangeRateById(
            @Parameter(description = "ID kursu waluty do pobrania") @PathVariable String id) {
        logger.info("Rozpoczynanie pobierania kursu waluty o ID: {}", id);
        ExchangeRateDto rate = exchangeRateService.getExchangeRateById(id);
        if (rate != null) {
            logger.info("Znaleziono kurs waluty o ID: {}", id);
        } else {
            logger.warn("Nie znaleziono kursu waluty o ID: {}", id);
        }
        return ResponseEntity.ok(rate);
    }

    @PostMapping
    @Operation(summary = "Dodaj nowy kurs waluty", description = "Tworzy nowy kurs waluty")
    @ApiResponse(responseCode = "201", description = "Pomyślnie dodano kurs waluty")
    public ResponseEntity<ExchangeRateDto> addExchangeRate(@RequestBody @Valid ExchangeRateDto exchangeRateDto) {
        logger.info("Rozpoczynanie dodawania nowego kursu waluty: {}", exchangeRateDto);
        ExchangeRateDto createdRate = exchangeRateService.addExchangeRate(exchangeRateDto);

        CurrencyPairUpdate updateMessage = new CurrencyPairUpdate();
        updateMessage.setCurrencyPair(createdRate.getCurrencyPair());
        updateMessage.setBuyRate(createdRate.getBuyRate());
        updateMessage.setSellRate(createdRate.getSellRate());
        updateMessage.setLastUpdated(createdRate.getLastUpdated());
        updateMessage.setAction("added");

        String[] currencyPairs = createdRate.getCurrencyPair().split(",");
        for (String pair : currencyPairs) {
            messagingTemplate.convertAndSend("/topic/exchange-rate/" + pair.trim(), updateMessage);
        }

        logger.info("Nowy kurs waluty został dodany: {}", createdRate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRate);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aktualizuj kurs waluty", description = "Aktualizuje istniejący kurs waluty")
    @ApiResponse(responseCode = "200", description = "Pomyślnie zaktualizowano kurs waluty")
    @ApiResponse(responseCode = "404", description = "Kurs waluty nie znaleziony")
    public ResponseEntity<ExchangeRateDto> updateExchangeRate(
            @Parameter(description = "ID kursu waluty do zaktualizowania") @PathVariable String id,
            @RequestBody @Valid ExchangeRateDto exchangeRateDto) {

        logger.info("Rozpoczynanie aktualizacji kursu waluty o ID: {}. Nowe dane: {}", id, exchangeRateDto);

        try {
            ExchangeRateDto updatedRate = exchangeRateService.updateExchangeRate(id, exchangeRateDto);

            CurrencyPairUpdate updateMessage = new CurrencyPairUpdate();
            updateMessage.setCurrencyPair(updatedRate.getCurrencyPair());
            updateMessage.setBuyRate(updatedRate.getBuyRate());
            updateMessage.setSellRate(updatedRate.getSellRate());
            updateMessage.setLastUpdated(updatedRate.getLastUpdated());
            updateMessage.setAction("updated");

            String[] currencyPairs = updatedRate.getCurrencyPair().split(",");  
            for (String pair : currencyPairs) {
                messagingTemplate.convertAndSend("/topic/exchange-rate/" + pair.trim(), updateMessage);
            }

            logger.info("Aktualizacja kursu waluty o ID: {} zakończona sukcesem.", id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedRate);

        } catch (EntityNotFoundException e) {
            logger.warn("Nie znaleziono kursu waluty o ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń kurs waluty", description = "Usuwa kurs waluty po jego ID")
    @ApiResponse(responseCode = "200", description = "Pomyślnie usunięto kurs waluty")
    @ApiResponse(responseCode = "404", description = "Kurs waluty nie znaleziony")
    public ResponseEntity<String> deleteExchangeRate(@Parameter(description = "ID kursu waluty do usunięcia") @PathVariable String id) {
        logger.info("Rozpoczynanie usuwania kursu waluty o ID: {}", id);
        exchangeRateService.deleteExchangeRate(id);
        logger.info("Kurs waluty o ID: {} został pomyślnie usunięty.", id);
        return ResponseEntity.ok("Kurs waluty o ID: " + id + " został pomyślnie usunięty.");
    }
}