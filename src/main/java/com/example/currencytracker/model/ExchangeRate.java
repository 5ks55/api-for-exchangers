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
@Document(collection = "exchange_rates")
public class ExchangeRate {
    @Id
    private String id;  // The unique identifier of the document in MongoDB.

    private String currencyPair;  // For example, ‘USD/EUR.’
    private Double buyRate;       // Buying rate.
    private Double sellRate;      // Sale rate
    private LocalDateTime lastUpdated; // The date and time of the last update.
}


