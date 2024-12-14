/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.dto;

/**
 *
 * @author Nazar
 */

import java.time.LocalDateTime;
import com.example.currencytracker.model.ExchangeRate;


public class ExchangeRateDto {

    private String currencyPair;
    private double buyRate;
    private double sellRate;
    private LocalDateTime lastUpdated;
    private int platformId;

    // Пустой конструктор (необходим для Jackson)
    public ExchangeRateDto() {
    }
    
    public ExchangeRateDto(ExchangeRate exchangeRate) {
        this.currencyPair = exchangeRate.getCurrencyPair();
        this.buyRate = exchangeRate.getBuyRate();
        this.sellRate = exchangeRate.getSellRate();
        this.lastUpdated = exchangeRate.getLastUpdated();
        this.platformId = exchangeRate.getPlatformId();
    }

    // Геттеры и сеттеры
    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(double buyRate) {
        this.buyRate = buyRate;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }
}