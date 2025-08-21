package com.aviral.journalApp.service;

import com.aviral.journalApp.entity.Journal;
import com.aviral.journalApp.entity.User;
import com.aviral.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalEntryService {

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveJournal(Journal journal, String userName) {
        User user = userService.findUserByUserName(userName);

        journal.setDate(LocalDateTime.now());
        Journal savedJournal = journalEntryRepository.save(journal);

        user.getJournals().add(savedJournal);
        userService.saveUser(user);
    }

    public void saveJournal(Journal journal) {
        journalEntryRepository.save(journal);
    }

    public List<Journal> getAllJournals() {
        return journalEntryRepository.findAll();
    }

    public Optional<Journal> getJournalById(String id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteJournalByID(String id, String userName) {
        User user = userService.findUserByUserName(userName);

        boolean isRemoved = user.getJournals().removeIf(journal -> journal.getId().equals(id));

        if (isRemoved) {
            userService.saveUser(user);

            journalEntryRepository.deleteById(id);
        }

        return isRemoved;
    }
}
