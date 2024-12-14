/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.service;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.model.ExchangePlatform;
import com.example.currencytracker.repository.ExchangePlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExchangePlatformService {

    @Autowired
    private ExchangePlatformRepository exchangePlatformRepository;

    // Pobierz wszystkie platformy wymiany
    public List<ExchangePlatform> getAllExchangePlatforms() {
        return exchangePlatformRepository.findAll();
    }

    // Pobierz platformę wymiany po ID
    public ExchangePlatform getExchangePlatformById(String id) {
        Optional<ExchangePlatform> platform = exchangePlatformRepository.findById(id);
        return platform.orElse(null);
    }

    // Dodaj nową platformę wymiany
    public ExchangePlatform addExchangePlatform(ExchangePlatform platform) {
        return exchangePlatformRepository.save(platform);
    }

    // Zaktualizuj dane platformy wymiany
    public ExchangePlatform updateExchangePlatform(String id, ExchangePlatform platform) {
        Optional<ExchangePlatform> existingPlatform = exchangePlatformRepository.findById(id);
        if (existingPlatform.isPresent()) {
            ExchangePlatform updatedPlatform = existingPlatform.get();
            updatedPlatform.setName(platform.getName());
            updatedPlatform.setParseUrl(platform.getParseUrl());
            return exchangePlatformRepository.save(updatedPlatform);
        }
        return null;
    }

    // Usuń platformę wymiany
    public void deleteExchangePlatform(String id) {
        exchangePlatformRepository.deleteById(id);
    }
}
