package com.aviral.journalApp.repository;

import com.aviral.journalApp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUserName(String username);
    void deleteByUserName(String username);
}
