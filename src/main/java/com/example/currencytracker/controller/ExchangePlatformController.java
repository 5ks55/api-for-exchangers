/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.controller;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.model.ExchangePlatform;
import com.example.currencytracker.service.ExchangePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-platforms")
public class ExchangePlatformController {

    @Autowired
    private ExchangePlatformService exchangePlatformService;

    @GetMapping
    public List<ExchangePlatform> getAllExchangePlatforms() {
        return exchangePlatformService.getAllExchangePlatforms();
    }

    @PostMapping
    public ExchangePlatform addExchangePlatform(@RequestBody ExchangePlatform platform) {
        return exchangePlatformService.addExchangePlatform(platform);
    }
}
