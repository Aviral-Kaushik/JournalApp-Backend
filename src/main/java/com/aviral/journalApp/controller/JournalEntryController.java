package com.aviral.journalApp.controller;

import com.aviral.journalApp.entity.Journal;
import com.aviral.journalApp.entity.User;
import com.aviral.journalApp.service.JournalEntryService;
import com.aviral.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Journal APIs", description = "Journal CRUD Operations endpoints")
@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @Operation(summary = "This endpoint will return all journals of specific user.")
    @GetMapping
    public ResponseEntity<?> getAllJournalOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findUserByUserName(username);
        List<Journal> journals = user.getJournals();

        if (journals != null)
            return new ResponseEntity<>(journals, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Journal> createEntry(@RequestBody Journal journal) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            journalEntryService.saveJournal(journal, username);
            return new ResponseEntity<>(journal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getJournalByID(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findUserByUserName(username);

        List<Journal> collect = user.getJournals()
                .stream()
                .filter(journal -> journal.getId().equals(id)).toList();

        if (!collect.isEmpty()) {
            Optional<Journal> journal = journalEntryService.getJournalById(id);

            if (journal.isPresent())
                return new ResponseEntity<>(journal.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteJournalByID(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean isRemoved = journalEntryService.deleteJournalByID(id, username);

        if (isRemoved)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> updateJournal(@RequestBody Journal journal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findUserByUserName(username);
        List<Journal> collect = user.getJournals()
                .stream()
                .filter(currentJournal -> currentJournal.getId().equals(journal.getId())).toList();

        if (collect.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Journal oldJournal = journalEntryService.getJournalById(journal.getId()).orElse(null);

        if (oldJournal == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        oldJournal.setTitle(!journal.getTitle().isEmpty() ? journal.getTitle() : oldJournal.getTitle());
        oldJournal.setContent(journal.getContent() != null && !journal.getContent().isEmpty() ? journal.getContent() : oldJournal.getContent());
        oldJournal.setSentiment(journal.getSentiment() != null ? journal.getSentiment() : oldJournal.getSentiment());

        journalEntryService.saveJournal(oldJournal);

        return new ResponseEntity<>(oldJournal, HttpStatus.OK);
    }
}
