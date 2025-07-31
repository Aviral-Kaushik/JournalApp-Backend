package com.aviral.journalApp.service;

import com.aviral.journalApp.entity.Journal;
import com.aviral.journalApp.entity.User;
import com.aviral.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalEntryService {

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
        userService.saveEntry(user);
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
    public void deleteJournalByID(String id, String userName) {
        User user = userService.findUserByUserName(userName);

        user.getJournals().removeIf(journal -> journal.getId().equals(id));
        userService.saveEntry(user);

        journalEntryRepository.deleteById(id);
    }
}
