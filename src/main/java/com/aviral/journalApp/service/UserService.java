package com.aviral.journalApp.service;

import com.aviral.journalApp.entity.User;
import com.aviral.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception: {}", String.valueOf(e));
        }
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User findUserByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public User updateUser(User oldUserData, User updatedUserData) {
        oldUserData.setUserName(updatedUserData.getUserName());
        oldUserData.setPassword(passwordEncoder.encode(updatedUserData.getPassword()));

        userRepository.save(oldUserData);

        return oldUserData;
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public User deleteUserByUsername(String username) {
        User user = findUserByUserName(username);
        if (user == null)
            return null;

        userRepository.deleteByUserName(username);
        return user;
    }
}
