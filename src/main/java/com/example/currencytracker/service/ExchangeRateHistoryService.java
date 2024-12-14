/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.service;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.model.ExchangeRateHistory;
import com.example.currencytracker.repository.ExchangeRateHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRateHistoryService {

    @Autowired
    private ExchangeRateHistoryRepository exchangeRateHistoryRepository;

    public List<ExchangeRateHistory> getAllExchangeRateHistories() {
        return exchangeRateHistoryRepository.findAll();
    }

    public ExchangeRateHistory getExchangeRateHistoryById(String id) {
        return exchangeRateHistoryRepository.findById(id).orElse(null);
    }

    public ExchangeRateHistory addExchangeRateHistory(ExchangeRateHistory history) {
        return exchangeRateHistoryRepository.save(history);
    }

    public void deleteById(String id) {
        exchangeRateHistoryRepository.deleteById(id);
    }
}

