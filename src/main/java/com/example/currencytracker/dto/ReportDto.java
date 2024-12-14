/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.dto;

/**
 *
 * @author Nazar
*/

import java.util.List;

public class ReportDto {

    private String userId;
    private List<String> currencyPair;
    private TimeRangeDto timeRange;

    public static class TimeRangeDto {
        private String start;
        private String end;

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(List<String> currencyPair) {
        this.currencyPair = currencyPair;
    }

    public TimeRangeDto getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRangeDto timeRange) {
        this.timeRange = timeRange;
    }
}
