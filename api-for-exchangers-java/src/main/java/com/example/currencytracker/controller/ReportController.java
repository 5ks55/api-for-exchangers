/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.controller;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.model.Report;
import com.example.currencytracker.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports Controller", description = "API do zarządzania raportami")
public class ReportController {

    private static final Logger logger = LogManager.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie raporty", description = "Zwraca listę wszystkich raportów")
    @ApiResponse(responseCode = "200", description = "Pomyślnie pobrano wszystkie raporty")
    public List<Report> getAllReports() {
        logger.info("Rozpoczynanie pobierania wszystkich raportów z bazy danych.");
        List<Report> reports = reportService.getAllReports();
        logger.info("Pobrano {} raportów.", reports.size());
        return reports;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz raport po ID", description = "Zwraca raport na podstawie ID")
    @ApiResponse(responseCode = "200", description = "Pomyślnie pobrano raport")
    @ApiResponse(responseCode = "404", description = "Raport nie został znaleziony")
    public Report getReportById(@Parameter(description = "ID raportu, który ma zostać pobrany") @PathVariable String id) {
        logger.info("Rozpoczynanie pobierania raportu z ID: {}", id);
        Report report = reportService.getReportById(id);
        if (report != null) {
            logger.info("Znaleziono raport z ID: {}", id);
        } else {
            logger.warn("Nie znaleziono raportu z ID: {}", id);
        }
        return report;
    }

    @PostMapping
    @Operation(summary = "Tworzenie nowego raportu", description = "Tworzy nowy raport")
    @ApiResponse(responseCode = "201", description = "Pomyślnie utworzono raport")
    public Report createReport(@RequestBody Report report) {
        logger.info("Rozpoczynanie tworzenia nowego raportu dla userId: {}", report.getUserId());
        Report createdReport = reportService.addReport(report);
        logger.info("Nowy raport został utworzony dla userId: {}", report.getUserId());
        return createdReport;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuwanie raportu", description = "Usuwa raport na podstawie ID")
    @ApiResponse(responseCode = "200", description = "Pomyślnie usunięto raport")
    @ApiResponse(responseCode = "404", description = "Raport nie został znaleziony")
    public String deleteReport(@Parameter(description = "ID raportu, który ma zostać usunięty") @PathVariable String id) {
        logger.info("Rozpoczynanie usuwania raportu z ID: {}", id);
        reportService.deleteReport(id);
        logger.info("Raport z ID: {} został pomyślnie usunięty.", id);
        return "Raport o ID " + id + " został pomyślnie usunięty.";
    }
}