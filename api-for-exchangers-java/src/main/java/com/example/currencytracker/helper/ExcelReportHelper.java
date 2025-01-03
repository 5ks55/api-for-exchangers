/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.helper;

/**
 *
 * @author IVAN
 */

import com.example.currencytracker.model.ExchangeRateHistory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelReportHelper {

    public static byte[] createExchangeRateReport(List<ExchangeRateHistory> histories) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Exchange Rate History");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Currency Pair", "Buy Rate", "Sell Rate", "Last Updated", "Platform ID"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }

            int rowNum = 1;
            for (ExchangeRateHistory history : histories) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(history.getId());
                row.createCell(1).setCellValue(history.getCurrencyPair());
                row.createCell(2).setCellValue(history.getBuyRate());
                row.createCell(3).setCellValue(history.getSellRate());
                row.createCell(4).setCellValue(history.getLastUpdated().toString());
                row.createCell(5).setCellValue(history.getPlatformId());
            }

            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                workbook.write(out);
                return out.toByteArray();
            }
        }
    }

    private static CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}
