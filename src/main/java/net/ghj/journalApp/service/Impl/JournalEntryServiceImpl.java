package net.ghj.journalApp.service.Impl;

import net.ghj.journalApp.entity.JournalEntry;
import net.ghj.journalApp.entity.User;
import net.ghj.journalApp.repository.JournalEntryRepository;
import net.ghj.journalApp.service.JournalEntryService;
import net.ghj.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryServiceImpl implements JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository;

    @Autowired
    UserService userService;

    @Override
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDate.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println("Exception:- " + e);
            throw new RuntimeException("An error occurred while saving the entry." + e);
        }
    }

    @Override
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    @Override
    public List<JournalEntry> showEntry() {
        return journalEntryRepository.findAll();
    }

    @Override
    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Override
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println("Exception:- " + e);
            throw new RuntimeException("An error occurred while deleting the entry." + e);
        }
        return removed;
    }
}
