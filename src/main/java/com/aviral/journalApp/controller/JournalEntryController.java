package com.aviral.journalApp.controller;

import com.aviral.journalApp.entity.Journal;
import com.aviral.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<Journal> getAll() {
        List<Journal> journals = journalEntryService.getAllJournals();

        if (journals != null && !journals.isEmpty())
            return journals;

        return new ArrayList<>();
    }

    @PostMapping
    public ResponseEntity<Journal> createEntry(@RequestBody Journal journal) {
        journal.setDate(LocalDateTime.now());
        journalEntryService.saveJournal(journal);

        return new ResponseEntity<>(journal, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Journal> getJournalByID(@PathVariable String id) {
        Optional<Journal> journal = journalEntryService.getJournalById(id);

        if (journal.isPresent())
            return new ResponseEntity<>(journal.get(), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteJournalByID(@PathVariable String id) {
        journalEntryService.deleteJournalByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> updateJournal(@RequestBody Journal journal) {
        Journal oldJournal = journalEntryService.getJournalById(journal.getId()).orElse(null);

        if (oldJournal != null) {
            oldJournal.setTitle(journal.getTitle() != null && !journal.getTitle().isEmpty() ? journal.getTitle() : oldJournal.getTitle());
            oldJournal.setContent(journal.getContent() != null && !journal.getContent().isEmpty() ? journal.getContent() : oldJournal.getContent());

            journalEntryService.saveJournal(oldJournal);

            return new ResponseEntity<>(oldJournal, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
