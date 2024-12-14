/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.repository;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.model.ExchangeRate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRateRepository extends MongoRepository<ExchangeRate, String> {
    List<ExchangeRate> findByCurrencyPair(String currencyPair);
}


