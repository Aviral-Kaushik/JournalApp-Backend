package com.aviral.journalApp.service;

import com.aviral.journalApp.entity.Journal;
import com.aviral.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveJournal(Journal journal) {
        try {
            journal.setDate(LocalDateTime.now());
            journalEntryRepository.save(journal);
        } catch (Exception e) {
            log.error("Exception: {}", String.valueOf(e));
        }
    }

    public List<Journal> getAllJournals() {
        return journalEntryRepository.findAll();
    }

    public Optional<Journal> getJournalById(String id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteJournalByID(String id) {
        journalEntryRepository.deleteById(id);
    }
}
