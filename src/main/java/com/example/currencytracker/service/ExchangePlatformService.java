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

@Service
public class ExchangePlatformService {

    @Autowired
    private ExchangePlatformRepository exchangePlatformRepository;

    public List<ExchangePlatform> getAllExchangePlatforms() {
        return exchangePlatformRepository.findAll();
    }

    public ExchangePlatform addExchangePlatform(ExchangePlatform platform) {
        return exchangePlatformRepository.save(platform);
    }
}
