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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private static final Logger logger = LogManager.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @GetMapping
    public List<Report> getAllReports() {
        logger.info("Rozpoczynanie pobierania wszystkich raportów z bazy danych.");
        List<Report> reports = reportService.getAllReports();
        logger.info("Pobrano {} raportów.", reports.size());
        return reports;
    }

    @GetMapping("/{id}")
    public Report getReportById(@PathVariable String id) {
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
    public Report createReport(@RequestBody Report report) {
        logger.info("Rozpoczynanie tworzenia nowego raportu dla userId: {}", report.getUserId());
        Report createdReport = reportService.addReport(report);
        logger.info("Nowy raport został utworzony dla userId: {}", report.getUserId());
        return createdReport;
    }

}