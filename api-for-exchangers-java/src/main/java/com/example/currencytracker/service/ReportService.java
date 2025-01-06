/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.service;

/**
 *
 * @author Nkt, Nazar
 */

import com.example.currencytracker.model.Report;
import com.example.currencytracker.repository.ReportRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private static final Logger logger = LogManager.getLogger(ReportService.class);

    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getAllReports() {
        logger.info("Pobieranie wszystkich raportów z bazy danych");
        return reportRepository.findAll();
    }

    public Report getReportById(String id) {
        logger.info("Szukanie raportu z ID: {}", id);
        Optional<Report> report = reportRepository.findById(id);
        return report.orElse(null);
    }

    public Report addReport(Report report) {
        logger.info("Zapis nowego raportu dla userId: {}", report.getUserId());
        return reportRepository.save(report);
    }

    public void deleteReport(String id) {
        logger.info("Usuwanie raportu z ID: {}", id);
        reportRepository.deleteById(id);
    }
}