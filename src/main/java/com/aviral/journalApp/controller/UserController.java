package com.aviral.journalApp.controller;

import com.aviral.journalApp.entity.User;
import com.aviral.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    private User createUser(@RequestBody User user) {
        userService.createUser(user);
        return user;
    }

    @GetMapping("{id}")
    private ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping
    private ResponseEntity<?> updateUser(@RequestBody User user) {
        User userData = userService.findUserByUserName(user.getUserName());

        if (userData != null) {
            User updatedUser = userService.updateUser(userData, user);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    private List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
