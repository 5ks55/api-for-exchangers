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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return convertToUserDtoList(users);
    }

    public UserDto getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToUserDto).orElse(null);
    }

    public User addUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setNotificationPreferences(convertToNotificationPreferences(userDto.getNotificationPreferences()));
        return userRepository.save(user);
    }

    public User updateUser(String id, UserDto userDto) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();

            if (userDto.getUsername() != null) {
                user.setUsername(userDto.getUsername());
            }
            if (userDto.getEmail() != null) {
                user.setEmail(userDto.getEmail());
            }

            if (userDto.getNotificationPreferences() != null) {
                User.NotificationPreferences currentPreferences = user.getNotificationPreferences();
                UserDto.NotificationPreferencesDto newPreferences = userDto.getNotificationPreferences();

                if (newPreferences.isEnabled() != currentPreferences.isEnabled()) {
                    currentPreferences.setEnabled(newPreferences.isEnabled());
                }

                if (newPreferences.getCurrencyPairs() != null) {
                    currentPreferences.setCurrencyPairs(newPreferences.getCurrencyPairs());
                }
            }

            return userRepository.save(user);
        } else {
            throw new RuntimeException("Użytkownik nie znaleziony");
        }
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    private User.NotificationPreferences convertToNotificationPreferences(UserDto.NotificationPreferencesDto dto) {
        User.NotificationPreferences preferences = new User.NotificationPreferences();
        preferences.setEnabled(dto.isEnabled());
        preferences.setCurrencyPairs(dto.getCurrencyPairs());
        return preferences;
    }

    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        UserDto.NotificationPreferencesDto preferencesDto = new UserDto.NotificationPreferencesDto();
        preferencesDto.setEnabled(user.getNotificationPreferences().isEnabled());
        preferencesDto.setCurrencyPairs(user.getNotificationPreferences().getCurrencyPairs());
        userDto.setNotificationPreferences(preferencesDto);
        
        return userDto;
    }

    private List<UserDto> convertToUserDtoList(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(convertToUserDto(user));
        }
        return userDtos;
    }
}