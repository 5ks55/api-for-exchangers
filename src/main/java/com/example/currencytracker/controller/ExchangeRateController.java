/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.controller;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.dto.ExchangeRateDto;
import com.example.currencytracker.model.ExchangeRate;
import com.example.currencytracker.service.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeRateController {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateController.class);

    @Autowired
    private ExchangeRateService exchangeRateService;

    // Pobierz wszystkie kursy walut
    @GetMapping
    public ResponseEntity<List<ExchangeRateDto>> getAllExchangeRates() {
        logger.info("Rozpoczynanie pobierania wszystkich kursów walut z bazy danych.");
        List<ExchangeRateDto> rates = exchangeRateService.getAllExchangeRates();
        logger.info("Pobrano {} kursów walut.", rates.size());
        return ResponseEntity.ok(rates);
    }

    // Pobierz kurs waluty po ID
    @GetMapping("/{id}")
    public ResponseEntity<ExchangeRateDto> getExchangeRateById(@PathVariable String id) {
        logger.info("Rozpoczynanie pobierania kursu waluty o ID: {}", id);
        ExchangeRateDto rate = exchangeRateService.getExchangeRateById(id);
        if (rate != null) {
            logger.info("Znaleziono kurs waluty o ID: {}", id);
        } else {
            logger.warn("Nie znaleziono kursu waluty o ID: {}", id);
        }
        return ResponseEntity.ok(rate);
    }

    // Dodaj nowy kurs waluty
    @PostMapping
    public ResponseEntity<ExchangeRateDto> addExchangeRate(@RequestBody @Valid ExchangeRateDto exchangeRateDto) {
        logger.info("Rozpoczynanie dodawania nowego kursu waluty: {}", exchangeRateDto);
        ExchangeRateDto createdRate = exchangeRateService.addExchangeRate(exchangeRateDto);
        logger.info("Nowy kurs waluty został dodany: {}", createdRate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRate);
    }

    // Zaktualizuj istniejący kurs waluty
    @PutMapping("/{id}")
    public ResponseEntity<ExchangeRateDto> updateExchangeRate(
            @PathVariable String id, 
            @RequestBody @Valid ExchangeRateDto exchangeRateDto) {
        logger.info("Rozpoczynanie aktualizacji kursu waluty o ID: {}. Nowe dane: {}", id, exchangeRateDto);
        ExchangeRateDto updatedRate = exchangeRateService.updateExchangeRate(id, exchangeRateDto);
        logger.info("Aktualizacja kursu waluty o ID: {} zakończona sukcesem.", id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedRate);
    }

    // Usuń kurs waluty
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExchangeRate(@PathVariable String id) {
        logger.info("Rozpoczynanie usuwania kursu waluty o ID: {}", id);
        exchangeRateService.deleteExchangeRate(id);
        logger.info("Kurs waluty o ID: {} został pomyślnie usunięty.", id);
        return ResponseEntity.ok("Kurs waluty o ID: " + id + " został pomyślnie usunięty.");
    }
}