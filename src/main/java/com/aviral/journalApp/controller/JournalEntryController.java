package com.aviral.journalApp.controller;

import com.aviral.journalApp.entity.Journal;
import com.aviral.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<Journal> getAll() {
        return journalEntryService.getAllJournals();
    }

    @PostMapping
    public Journal createEntry(@RequestBody Journal journal) {
        journal.setDate(LocalDateTime.now());
        journalEntryService.saveJournal(journal);
        return journal;
    }

    @GetMapping("{id}")
    public Journal getJournalByID(@PathVariable String id) {
        return journalEntryService.getJournalById(id).orElse(null);
    }

    @DeleteMapping("{id}")
    public boolean deleteJournalByID(@PathVariable String id) {
        journalEntryService.deleteJournalByID(id);
        return true;
    }

    @PutMapping
    public Journal updateJournal(@RequestBody Journal journal) {
        Journal oldJournal = journalEntryService.getJournalById(journal.getId()).orElse(null);

        if (oldJournal != null) {
            oldJournal.setTitle(journal.getTitle() != null && !journal.getTitle().isEmpty() ? journal.getTitle() : oldJournal.getTitle());
            oldJournal.setContent(journal.getContent() != null && !journal.getContent().isEmpty() ? journal.getContent() : oldJournal.getContent());

            journalEntryService.saveJournal(oldJournal);
        }

        return oldJournal;
    }
}
