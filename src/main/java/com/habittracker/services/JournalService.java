package com.habittracker.services;

import com.habittracker.models.journal.JournalEntry;
import com.habittracker.models.user.User;
import java.util.ArrayList;
import java.util.List;

public class JournalService {

    public void addEntry(String title, String body) {
        User currentUser = UserService.getCurrentUser();
        if (currentUser == null)
            return;

        List<JournalEntry> entries = getAllEntries();
        entries.add(new JournalEntry(title, body));
        DataStore.saveJournal(currentUser.getUsername(), entries);
    }

    public List<JournalEntry> getAllEntries() {
        User currentUser = UserService.getCurrentUser();
        if (currentUser == null)
            return new ArrayList<>();
        return DataStore.loadJournal(currentUser.getUsername());
    }

    public List<JournalEntry> search(String keyWord) {
        List<JournalEntry> result = new ArrayList<>();
        for (JournalEntry e : getAllEntries()) {
            if (e.getTitle().contains(keyWord) || e.getBody().contains(keyWord)) {
                result.add(e);
            }
        }
        return result;
    }

    public void deleteEntry(JournalEntry entry) {
        User currentUser = UserService.getCurrentUser();
        if (currentUser == null)
            return;

        List<JournalEntry> entries = getAllEntries();
        // Since we are loading fresh list, 'entry' object might not match identity.
        // But JournalEntry uses default equals (object ref). This is problem.
        // Use equals if implemented, or reimplement.
        // Assuming JournalEntry is just data.
        // I will implement equals/hashCode in JournalEntry? Or just iterate and match
        // fields?
        // Simpler: assume the UI passes an object from the list.
        // But if I reload logic, the object refs change.
        // Actually, if I load list, remove, then save, it should work IF 'entry' is in
        // 'entries'.
        // But 'entries' here is a NEW list from JSON. 'entry' is from OLD list in UI.
        // So 'entries.remove(entry)' will FAIL unless equals is overridden.

        // Quick fix: Remove by matching title/body?
        entries.removeIf(e -> e.getTitle().equals(entry.getTitle()) && e.getBody().equals(entry.getBody()));
        DataStore.saveJournal(currentUser.getUsername(), entries);
    }

    public void editEntry(JournalEntry original, String newTitle, String newBody) {
        User currentUser = UserService.getCurrentUser();
        if (currentUser == null)
            return;

        List<JournalEntry> entries = getAllEntries();
        for (JournalEntry e : entries) {
            if (e.getTitle().equals(original.getTitle()) && e.getBody().equals(original.getBody())) {
                e.setTitle(newTitle);
                e.setBody(newBody);
                break;
            }
        }
        DataStore.saveJournal(currentUser.getUsername(), entries);
    }
}
