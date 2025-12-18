package com.habittracker.models;

import java.util.*;
import com.habittracker.models.habits.*;
import com.habittracker.models.tasks.*;
import com.habittracker.models.journal.*;

public class Tracker {
    private static Tracker instance;
    private Map<String, Habit> habits;
    private List<Task> todayTasks;
    private List<JournalEntry> journalEntries;

    private Tracker() {
        habits = new HashMap<>();
        todayTasks = new ArrayList<>();
        journalEntries = new ArrayList<>();
    }

    public static Tracker getInstance() {
        if (instance == null) {
            instance = new Tracker();
        }
        return instance;
    }

    public Map<String, Habit> getHabits() {
        return habits;
    }

    public List<Task> getTodayTasks() {
        return todayTasks;
    }

    public List<JournalEntry> getJournalEntries() {
        return journalEntries;
    }
}
