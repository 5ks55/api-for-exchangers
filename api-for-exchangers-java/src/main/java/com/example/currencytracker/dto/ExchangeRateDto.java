/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.dto;

/**
 *
 * @author Nazar
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import com.example.currencytracker.model.ExchangeRate;


public class ExchangeRateDto {

    private String id;
    private String currencyPair;
    private double buyRate;
    private double sellRate;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdated;
    
    private int platformId;

    public ExchangeRateDto() {
    }
    
    public ExchangeRateDto(ExchangeRate exchangeRate) {
        this.id = exchangeRate.getId();
        this.currencyPair = exchangeRate.getCurrencyPair();
        this.buyRate = exchangeRate.getBuyRate();
        this.sellRate = exchangeRate.getSellRate();
        this.lastUpdated = exchangeRate.getLastUpdated();
        this.platformId = exchangeRate.getPlatformId();
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
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