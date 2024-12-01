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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping
    public List<ExchangeRate> getAllExchangeRates() {
        return exchangeRateService.getAllExchangeRates();
    }

    @PostMapping
    public ExchangeRate addExchangeRate(@RequestBody ExchangeRate exchangeRate) {
        return exchangeRateService.addExchangeRate(exchangeRate);
    }
}
