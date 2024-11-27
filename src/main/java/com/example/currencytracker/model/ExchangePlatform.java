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

@Data
@Document(collection = "exchange_platforms")
public class ExchangePlatform {
    @Id
    private String id;

    private String name;  // Name of platform
    private String apiUrl; // A link to an API or URL for parsing.
}



