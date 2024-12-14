/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.service;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.dto.UserDto;
import com.example.currencytracker.model.User;
import com.example.currencytracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Pobierz wszystkich użytkowników
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Pobierz użytkownika po ID
    public User getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    // Dodaj nowego użytkownika
    public User addUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        // Konwertujemy NotificationPreferencesDto na NotificationPreferences
        user.setNotificationPreferences(convertToNotificationPreferences(userDto.getNotificationPreferences()));
        
        return userRepository.save(user);
    }

    // Zaktualizuj dane użytkownika
    public User updateUser(String id, UserDto userDto) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setNotificationPreferences(convertToNotificationPreferences(userDto.getNotificationPreferences()));
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Użytkownik nie znaleziony");
        }
    }

    // Usuń użytkownika
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    // Konwertowanie NotificationPreferencesDto na NotificationPreferences
    private User.NotificationPreferences convertToNotificationPreferences(UserDto.NotificationPreferencesDto dto) {
        User.NotificationPreferences preferences = new User.NotificationPreferences();
        preferences.setEnabled(dto.isEnabled());
        preferences.setCurrencyPairs(dto.getCurrencyPairs());
        return preferences;
    }
}