/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.model;

/**
 *
 * @author Nkt
 */

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "exchange_rates")
public class ExchangeRate {
    @Id
    private String id;

    private String currencyPair;
    private Double buyRate;
    private Double sellRate;
    private LocalDateTime lastUpdated;
    private String platformId;   
}


