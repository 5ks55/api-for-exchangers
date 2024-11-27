/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.model;

/**
 *
 * @author Nkt
 */

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "exchange_rate_history")
public class ExchangeRateHistory {
    @Id
    private String id;

    private String exchangeRateId;  // Link to ExchangeRate.
    private Double rate;            // The currency exchange rate at the time of the update.
    private LocalDateTime timestamp; // The time to update the currency rate
}
