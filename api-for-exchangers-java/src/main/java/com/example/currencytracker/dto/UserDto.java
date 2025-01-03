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

public class UserDto {

    private String id;
    private String username;
    private String email;
    private NotificationPreferencesDto notificationPreferences;

    public static class NotificationPreferencesDto {
        private boolean enabled;
        private List<String> currencyPairs;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public List<String> getCurrencyPairs() {
            return currencyPairs;
        }

        public void setCurrencyPairs(List<String> currencyPairs) {
            this.currencyPairs = currencyPairs;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NotificationPreferencesDto getNotificationPreferences() {
        return notificationPreferences;
    }

    public void setNotificationPreferences(NotificationPreferencesDto notificationPreferences) {
        this.notificationPreferences = notificationPreferences;
    }
}
