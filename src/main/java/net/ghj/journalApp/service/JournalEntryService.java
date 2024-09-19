package net.ghj.journalApp.service;

import net.ghj.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface JournalEntryService {

    @Transactional
    void saveEntry(JournalEntry journalEntry, String userName);

    void saveEntry(JournalEntry journalEntry);

    List<JournalEntry> showEntry();

    Optional<JournalEntry> findById(ObjectId id);

    @Transactional
    boolean deleteById(ObjectId id, String userName);
}
