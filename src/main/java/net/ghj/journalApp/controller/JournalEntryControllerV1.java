package net.ghj.journalApp.controller;

import net.ghj.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("_journal")
public class JournalEntryControllerV1 {
    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @PostMapping("create-entry")
    public boolean createEntry(@RequestBody JournalEntry journalEntry){
        // @RequestBody :- It's like saying, "hey spring, please take the data from the request and turn it into a java object that I can use in my code."
//        this.journalEntries.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @GetMapping("get-all-entry")
    public ArrayList<JournalEntry> getAllEntry(){
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable Long myId) {
        return journalEntries.get(myId);
    }

    @DeleteMapping("/id/{myId}")
    public JournalEntry deleteJournalEntryById(@PathVariable Long myId) {
        return journalEntries.remove(myId);
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateJournalEntryById(@PathVariable Long id, @RequestBody JournalEntry myEntry) {
        return journalEntries.put(id, myEntry);
    }
}
