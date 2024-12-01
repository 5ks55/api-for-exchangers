/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.controller;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.model.ExchangeRateHistory;
import com.example.currencytracker.service.ExchangeRateHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-rate-history")
public class ExchangeRateHistoryController {

    @Autowired
    private ExchangeRateHistoryService exchangeRateHistoryService;

    @GetMapping
    public List<ExchangeRateHistory> getAllExchangeRateHistories() {
        return exchangeRateHistoryService.getAllExchangeRateHistories();
    }

    @PostMapping
    public ExchangeRateHistory addExchangeRateHistory(@RequestBody ExchangeRateHistory history) {
        return exchangeRateHistoryService.addExchangeRateHistory(history);
    }
}