/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.dto;

/**
 *
 * @author Nazar
 */

public class ExchangePlatformDto {

    private String id;
    private String name;
    private String parseUrl;

    // Konstruktor bezargumentowy
    public ExchangePlatformDto() {
    }

    // Konstruktor z argumentami
    public ExchangePlatformDto(String id, String name, String parseUrl) {
        this.id = id;
        this.name = name;
        this.parseUrl = parseUrl;
    }

    // Gettery i settery
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParseUrl() {
        return parseUrl;
    }

    public void setParseUrl(String parseUrl) {
        this.parseUrl = parseUrl;
    }
}