package com.aviral.journalApp.controller;

import com.aviral.journalApp.api.response.WeatherResponse;
import com.aviral.journalApp.entity.User;
import com.aviral.journalApp.service.UserService;
import com.aviral.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    private ResponseEntity<User> getUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findUserByUserName(authentication.getName());

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping
    private ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userData = userService.findUserByUserName(userName);

        if (userData != null) {
            User updatedUser = userService.updateUser(userData, user);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @GetMapping
//    private List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }

    @DeleteMapping
    private ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.deleteUserByUsername(userName);

        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("greet")
    private ResponseEntity<?> greet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        WeatherResponse weatherResponse = weatherService.getWeather("Meerut");

        if (weatherResponse == null)
            return new ResponseEntity<>("Hi " + authentication.getName(), HttpStatus.OK);

        return new ResponseEntity<>("Hi " + authentication.getName() + ", Weather feels like: " + weatherResponse.getCurrent().getFeelslike() + " °C.", HttpStatus.OK);
    }
}
