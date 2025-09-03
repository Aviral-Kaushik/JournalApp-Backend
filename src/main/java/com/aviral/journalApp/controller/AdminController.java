package com.aviral.journalApp.controller;

import com.aviral.journalApp.entity.User;
import com.aviral.journalApp.repository.UserRepositoryImpl;
import com.aviral.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin APIs", description = "Admin Role related APIs")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @GetMapping("/get-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();

        if (users == null || users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdminUser(@RequestParam String username) {
        try {
            User user = userService.createAdminUser(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>("User Not Found", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/revoke-admin-access")
    public ResponseEntity<?> revokeAdminAccess(@RequestParam String username) {
        try {
            User user = userService.revokeAdminAccess(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>("User Not Found", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("get-users-for-sentiment-analysis")
    public ResponseEntity<List<User>> getUsersForSentimentAnalysis() {
        return new ResponseEntity<>(userRepository.getUsersForSentimentAnalysis(), HttpStatus.OK);
    }

}
