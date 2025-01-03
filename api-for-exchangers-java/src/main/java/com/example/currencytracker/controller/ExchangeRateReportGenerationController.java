/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.controller;

/**
 *
 * @author IVAN
 */

import com.example.currencytracker.helper.ExcelReportHelper;
import com.example.currencytracker.model.ExchangeRateHistory;
import com.example.currencytracker.service.ExchangeRateHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Historical Reports Controller", description = "API do generowania raportów")
public class ExchangeRateReportGenerationController {

    @Autowired
    private ExchangeRateHistoryService exchangeRateHistoryService;

    @GetMapping("/exchange-rate-history")
    @Operation(summary = "Generowanie raportu historii kursów walut",
               description = "Zwraca raport w formacie Excel z uwzględnieniem przekazanych parametrów",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Raport został pomyślnie utworzony"),
                   @ApiResponse(responseCode = "500", description = "Błąd serwera podczas tworzenia raportu")
               })
    public ResponseEntity<byte[]> generateExchangeRateReport(
            @RequestParam("currencyPair") String currencyPair,
            @RequestParam("start") String start,
            @RequestParam("end") String end,
            @RequestParam("userId") Long userId) {
        try {
            OffsetDateTime startTime = OffsetDateTime.parse(start);
            OffsetDateTime endTime = OffsetDateTime.parse(end);

            List<ExchangeRateHistory> histories = exchangeRateHistoryService.getAllExchangeRateHistories().stream()
                    .filter(h -> h.getCurrencyPair().equals(currencyPair) &&
                                 h.getLastUpdated().atZone(ZoneId.systemDefault()).toOffsetDateTime().isAfter(startTime) &&
                                 h.getLastUpdated().atZone(ZoneId.systemDefault()).toOffsetDateTime().isBefore(endTime))
                    .toList();

            byte[] reportData = ExcelReportHelper.createExchangeRateReport(histories);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=exchange_rate_report.xlsx");
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

            return new ResponseEntity<>(reportData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}