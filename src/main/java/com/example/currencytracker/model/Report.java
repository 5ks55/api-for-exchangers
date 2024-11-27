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
import java.util.List;


@Data
@Document(collection = "reports")
public class Report {
    @Id
    private String id;

    private String reportType;  // Report type (e.g. ‘Daily’, ‘Weekly’).
    private LocalDateTime createdAt; // The date and time the report was created.
    private List<String> currencyPairs; // Currency pairs included.
    private String dateRange;  // Specified time range (e.g., ‘01-11-2024 to 10-11-2024’).
}
