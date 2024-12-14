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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Otrzymano żądanie pobrania wszystkich użytkowników.");
        List<User> users = userService.getAllUsers();
        logger.debug("Zwrócono {} użytkowników.", users.size());
        return users;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
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
    public User addUser(@RequestBody UserDto userDto) {
        logger.info("Otrzymano żądanie dodania nowego użytkownika.");
        User user = userService.addUser(userDto);
        logger.info("Nowy użytkownik został zapisany z ID: {}", user.getId());
        return user;
    }

}
