/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.controller;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.dto.ExchangeRateHistoryDto;
import com.example.currencytracker.model.ExchangeRateHistory;
import com.example.currencytracker.service.ExchangeRateHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exchange-rate-history")
@Tag(name = "Exchange Rate History Controller", description = "API do zarządzania historią kursów walut")
public class ExchangeRateHistoryController {

    private static final Logger logger = LogManager.getLogger(ExchangeRateHistoryController.class);

    @Autowired
    private ExchangeRateHistoryService exchangeRateHistoryService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie historie kursów", description = "Zwraca listę wszystkich historii kursów walut")
    @ApiResponse(responseCode = "200", description = "Pomyślnie pobrano wszystkie historie kursów")
    public List<ExchangeRateHistoryDto> pobierzWszystkieHistorieKursow() {
        logger.info("Otrzymano żądanie pobrania wszystkich historii kursów walut.");
        List<ExchangeRateHistoryDto> historie = exchangeRateHistoryService.getAllExchangeRateHistories()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        logger.debug("Zwrócono {} rekordów historii kursów walut.", historie.size());
        return historie;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz historię kursu waluty po ID", description = "Zwraca historię kursu waluty na podstawie ID")
    @ApiResponse(responseCode = "200", description = "Pomyślnie pobrano historię kursu")
    @ApiResponse(responseCode = "404", description = "Historia kursu o podanym ID nie została znaleziona")
    public ExchangeRateHistoryDto pobierzHistorieKursuPoId(
            @Parameter(description = "ID historii kursu waluty do pobrania") @PathVariable String id) {
        logger.info("Otrzymano żądanie pobrania historii kursu waluty o ID: {}", id);
        ExchangeRateHistory historia = exchangeRateHistoryService.getExchangeRateHistoryById(id);
        if (historia != null) {
            logger.debug("Zwrócono historię kursu: {}", historia);
            return convertToDto(historia);
        } else {
            logger.warn("Nie znaleziono historii kursu dla ID: {}", id);
            return null;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń historię kursu", description = "Usuwa historię kursu waluty na podstawie ID")
    @ApiResponse(responseCode = "200", description = "Pomyślnie usunięto historię kursu")
    @ApiResponse(responseCode = "404", description = "Historia kursu o podanym ID nie została znaleziona")
    public String usunHistorieKursu(@Parameter(description = "ID historii kursu waluty do usunięcia") @PathVariable String id) {
        logger.info("Otrzymano żądanie usunięcia historii kursu waluty o ID: {}", id);
        try {
            exchangeRateHistoryService.deleteById(id);
            logger.info("Historia kursu waluty o ID {} została pomyślnie usunięta.", id);
            return "Historia kursu " + id + " waluty usunięta pomyślnie.";
        } catch (Exception e) {
            logger.error("Wystąpił błąd podczas usuwania historii kursu waluty o ID: {}", id, e);
            return "Nie udało się usunąć historii kursu waluty.";
        }
    }

    @PostMapping
    @Operation(summary = "Dodaj historię kursu", description = "Tworzy nową historię kursu waluty")
    @ApiResponse(responseCode = "201", description = "Pomyślnie utworzono historię kursu waluty")
    public ExchangeRateHistoryDto dodajHistorieKursu(@RequestBody ExchangeRateHistoryDto dto) {
        logger.info("Otrzymano żądanie dodania nowej historii kursu waluty: {}", dto);
        ExchangeRateHistory historia = exchangeRateHistoryService.addExchangeRateHistory(convertToEntity(dto));
        logger.info("Nowa historia kursu waluty została zapisana z ID: {}", historia.getId());
        return convertToDto(historia);
    }

    private ExchangeRateHistoryDto convertToDto(ExchangeRateHistory history) {
        return new ExchangeRateHistoryDto(
                history.getId(),
                history.getCurrencyPair(),
                history.getBuyRate(),
                history.getSellRate(),
                history.getLastUpdated(),
                history.getPlatformId()
        );
    }

    private ExchangeRateHistory convertToEntity(ExchangeRateHistoryDto dto) {
        ExchangeRateHistory history = new ExchangeRateHistory();
        history.setId(dto.getId());
        history.setCurrencyPair(dto.getCurrencyPair());
        history.setBuyRate(dto.getBuyRate());
        history.setSellRate(dto.getSellRate());
        history.setLastUpdated(dto.getLastUpdated());
        history.setPlatformId(dto.getPlatformId());
        return history;
    }
}