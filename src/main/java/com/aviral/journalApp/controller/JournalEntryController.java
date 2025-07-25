package com.aviral.journalApp.controller;

import com.aviral.journalApp.entity.Journal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    private Map<Long, Journal> journals = new HashMap<>();

    @GetMapping
    public List<Journal> getAll() {
        return new ArrayList<>(journals.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody Journal journal) {
        journals.put(journal.getId(), journal);
        return true;
    }

    @GetMapping("{id}")
    public Journal getJournalByID(@PathVariable Long id) {
        return journals.get(id);
    }

    @DeleteMapping("{id}")
    public Journal deleteJournalByID(@PathVariable Long id) {
        return journals.remove(id);
    }

    @PutMapping
    public Journal updateJournal(@RequestBody Journal journal) {
        return journals.put(journal.getId(), journal);
    }
}
