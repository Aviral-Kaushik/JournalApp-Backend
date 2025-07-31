package com.aviral.journalApp.controller;

import com.aviral.journalApp.entity.Journal;
import com.aviral.journalApp.entity.User;
import com.aviral.journalApp.service.JournalEntryService;
import com.aviral.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalOfUser(@RequestParam String userName) {
        User user = userService.findUserByUserName(userName);
        List<Journal> journals = user.getJournals();

        if (journals != null)
            return new ResponseEntity<>(journals, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Journal> createEntry(@RequestBody Journal journal, @RequestParam String userName) {
        try {
            journalEntryService.saveJournal(journal, userName);
            return new ResponseEntity<>(journal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Journal> getJournalByID(@PathVariable String id) {
        Optional<Journal> journal = journalEntryService.getJournalById(id);

        if (journal.isPresent())
            return new ResponseEntity<>(journal.get(), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteJournalByID(@PathVariable String id, @RequestParam String userName) {
        journalEntryService.deleteJournalByID(id, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> updateJournal(@RequestBody Journal journal, @RequestParam String userName) {
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
