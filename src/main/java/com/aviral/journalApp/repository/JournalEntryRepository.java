package com.aviral.journalApp.repository;

import com.aviral.journalApp.entity.Journal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<Journal, String> {
}
