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

public class ExchangeRateHistoryDto {

    private String id;
    private String currencyPair;
    private Double buyRate;
    private Double sellRate;
    private LocalDateTime lastUpdated;
    private String platformId;

    public ExchangeRateHistoryDto() {
    }

    public ExchangeRateHistoryDto(String id, String currencyPair, Double buyRate, Double sellRate, LocalDateTime lastUpdated, String platformId) {
        this.id = id;
        this.currencyPair = currencyPair;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.lastUpdated = lastUpdated;
        this.platformId = platformId;
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

    public Double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(Double buyRate) {
        this.buyRate = buyRate;
    }

    public Double getSellRate() {
        return sellRate;
    }

    public void setSellRate(Double sellRate) {
        this.sellRate = sellRate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }
}
