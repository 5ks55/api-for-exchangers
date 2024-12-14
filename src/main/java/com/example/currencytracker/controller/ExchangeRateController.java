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
        logger.info("Żądanie pobrania wszystkich kursów walut");
        List<ExchangeRateDto> rates = exchangeRateService.getAllExchangeRates();
        return ResponseEntity.ok(rates);
    }

    // Pobierz kurs waluty po ID
    @GetMapping("/{id}")
    public ResponseEntity<ExchangeRateDto> getExchangeRateById(@PathVariable String id) {
        logger.info("Żądanie pobrania kursu waluty o ID: {}", id);
        ExchangeRateDto rate = exchangeRateService.getExchangeRateById(id);
        return ResponseEntity.ok(rate);
    }

}
