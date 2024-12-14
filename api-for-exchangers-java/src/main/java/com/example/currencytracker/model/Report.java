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

    private String userId;
    private List<String> currencyPair;
    private TimeRange timeRange;
    private LocalDateTime createdOn;

    @Data
    public static class TimeRange {
        private LocalDateTime start;
        private LocalDateTime end;
    }
}