package com.aviral.journalApp.service;

import com.aviral.journalApp.entity.Journal;
import com.aviral.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveJournal(Journal journal) {
        journalEntryRepository.save(journal);
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
