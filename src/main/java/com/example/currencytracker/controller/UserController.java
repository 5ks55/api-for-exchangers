/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.currencytracker.controller;

/**
 *
 * @author Nkt
 */

import com.example.currencytracker.dto.UserDto;
import com.example.currencytracker.model.User;
import com.example.currencytracker.service.UserService;
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
@RequestMapping("/api/users")
@Tag(name = "Users Controller", description = "API do zarządzania użytkownikami")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkich użytkowników", description = "Zwraca listę wszystkich użytkowników")
    @ApiResponse(responseCode = "200", description = "Pomyślnie pobrano wszystkich użytkowników")
    public List<User> getAllUsers() {
        logger.info("Otrzymano żądanie pobrania wszystkich użytkowników.");
        List<User> users = userService.getAllUsers();
        logger.debug("Zwrócono {} użytkowników.", users.size());
        return users;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz użytkownika po ID", description = "Zwraca użytkownika po jego ID")
    @ApiResponse(responseCode = "200", description = "Pomyślnie pobrano użytkownika")
    @ApiResponse(responseCode = "404", description = "Użytkownik nie został znaleziony")
    public User getUserById(@Parameter(description = "ID użytkownika, który ma zostać pobrany") @PathVariable String id) {
        logger.info("Otrzymano żądanie pobrania użytkownika o ID: {}", id);
        User user = userService.getUserById(id);
        if (user != null) {
            logger.debug("Zwrócono użytkownika: {}", user);
        } else {
            logger.warn("Nie znaleziono użytkownika o ID: {}", id);
        }
        return user;
    }

    @PostMapping
    @Operation(summary = "Dodaj nowego użytkownika", description = "Tworzy nowego użytkownika")
    @ApiResponse(responseCode = "201", description = "Pomyślnie utworzono użytkownika")
    public User addUser(@RequestBody UserDto userDto) {
        logger.info("Otrzymano żądanie dodania nowego użytkownika.");
        User user = userService.addUser(userDto);
        logger.info("Nowy użytkownik został zapisany z ID: {}", user.getId());
        return user;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aktualizuj użytkownika", description = "Aktualizuje dane istniejącego użytkownika")
    @ApiResponse(responseCode = "200", description = "Pomyślnie zaktualizowano użytkownika")
    @ApiResponse(responseCode = "404", description = "Użytkownik nie został znaleziony")
    public User updateUser(@Parameter(description = "ID użytkownika, który ma zostać zaktualizowany") @PathVariable String id, 
                           @RequestBody UserDto userDto) {
        logger.info("Otrzymano żądanie aktualizacji danych użytkownika o ID: {}", id);
        User updatedUser = userService.updateUser(id, userDto);
        logger.info("Zaktualizowano dane użytkownika o ID: {}", updatedUser.getId());
        return updatedUser;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń użytkownika", description = "Usuwa użytkownika po jego ID")
    @ApiResponse(responseCode = "200", description = "Pomyślnie usunięto użytkownika")
    @ApiResponse(responseCode = "404", description = "Użytkownik nie został znaleziony")
    public String deleteUser(@Parameter(description = "ID użytkownika, który ma zostać usunięty") @PathVariable String id) {
        logger.info("Otrzymano żądanie usunięcia użytkownika o ID: {}", id);
        try {
            userService.deleteUser(id);
            logger.info("Użytkownik o ID {} został pomyślnie usunięty.", id);
            return "Użytkownik o ID " + id + " usunięty pomyślnie.";
        } catch (Exception e) {
            logger.error("Wystąpił błąd podczas usuwania użytkownika o ID: {}", id, e);
            return "Nie udało się usunąć użytkownika.";
        }
    }
}
