/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.repository;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.model.ExchangeRateHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExchangeRateHistoryRepository extends MongoRepository<ExchangeRateHistory, String> {
    
}