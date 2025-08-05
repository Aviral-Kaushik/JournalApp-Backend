package com.aviral.journalApp.controller;

import com.aviral.journalApp.entity.User;
import com.aviral.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("health")
    public String healthCheck() {
        return "Ok!";
    }

    @PostMapping("create-user")
    private User createUser(@RequestBody User user) {
        userService.createUser(user);
        return user;
    }
}
