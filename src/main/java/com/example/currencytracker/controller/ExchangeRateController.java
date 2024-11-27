/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.controller;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.model.ExchangeRate;
import com.example.currencytracker.service.ExchangeRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeRateController {
    private final ExchangeRateService service;

    public ExchangeRateController(ExchangeRateService service) {
        this.service = service;
    }

    @GetMapping
    public List<ExchangeRate> getAllExchangeRates() {
        return service.getAllExchangeRates();
    }

    @PostMapping
    public ResponseEntity<ExchangeRate> saveExchangeRate(@RequestBody ExchangeRate exchangeRate) {
        return ResponseEntity.ok(service.saveExchangeRate(exchangeRate));
    }
}
