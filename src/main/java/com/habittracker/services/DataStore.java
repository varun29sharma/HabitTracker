package com.habittracker.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.habittracker.models.user.User;
import com.habittracker.models.habits.Habit;
import com.habittracker.models.tasks.Task;
import com.habittracker.models.journal.JournalEntry;
import com.habittracker.utils.LocalDateAdapter;
import com.habittracker.utils.DateAdapter;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataStore {
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = "data/users.json";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(Date.class, new DateAdapter())
            .create();

    static {
        new File(DATA_DIR).mkdirs();
    }

    public static void saveUsers(List<User> users) {
        try (Writer writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> loadUsers() {
        File file = new File(USERS_FILE);
        if (!file.exists())
            return new ArrayList<>();
        try (Reader reader = new FileReader(file)) {
            List<User> users = gson.fromJson(reader, new TypeToken<List<User>>() {
            }.getType());
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // HABITS
    public static void saveHabits(String username, List<Habit> habits) {
        try (Writer writer = new FileWriter(DATA_DIR + "/habits_" + username + ".json")) {
            gson.toJson(habits, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Habit> loadHabits(String username) {
        File file = new File(DATA_DIR + "/habits_" + username + ".json");
        if (!file.exists() || file.length() == 0)
            return new ArrayList<>();
        try (Reader reader = new FileReader(file)) {
            String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            if (content.trim().isEmpty() || content.trim().equals("[]") || content.trim().equals("null")) {
                return new ArrayList<>();
            }
            List<Habit> list = gson.fromJson(content, new TypeToken<List<Habit>>() {
            }.getType());
            return list != null ? list : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error loading habits for " + username + ": " + e.getMessage());
            e.printStackTrace();
            // If file is corrupted, backup and create new one
            try {
                if (file.exists()) {
                    file.renameTo(new File(DATA_DIR + "/habits_" + username + ".json.bak"));
                }
            } catch (Exception ex) {
                // Ignore backup errors
            }
            return new ArrayList<>();
        }
    }

    // TASKS
    public static void saveTasks(String username, List<Task> tasks) {
        try (Writer writer = new FileWriter(DATA_DIR + "/tasks_" + username + ".json")) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Task> loadTasks(String username) {
        File file = new File(DATA_DIR + "/tasks_" + username + ".json");
        if (!file.exists() || file.length() == 0)
            return new ArrayList<>();
        try (Reader reader = new FileReader(file)) {
            String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            if (content.trim().isEmpty() || content.trim().equals("[]") || content.trim().equals("null")) {
                return new ArrayList<>();
            }
            List<Task> list = gson.fromJson(content, new TypeToken<List<Task>>() {
            }.getType());
            return list != null ? list : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error loading tasks for " + username + ": " + e.getMessage());
            e.printStackTrace();
            try {
                if (file.exists()) {
                    file.renameTo(new File(DATA_DIR + "/tasks_" + username + ".json.bak"));
                }
            } catch (Exception ex) {
                // Ignore backup errors
            }
            return new ArrayList<>();
        }
    }

    // JOURNAL
    public static void saveJournal(String username, List<JournalEntry> entries) {
        try (Writer writer = new FileWriter(DATA_DIR + "/journal_" + username + ".json")) {
            gson.toJson(entries, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<JournalEntry> loadJournal(String username) {
        File file = new File(DATA_DIR + "/journal_" + username + ".json");
        if (!file.exists() || file.length() == 0)
            return new ArrayList<>();
        try (Reader reader = new FileReader(file)) {
            String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            if (content.trim().isEmpty() || content.trim().equals("[]") || content.trim().equals("null")) {
                return new ArrayList<>();
            }
            List<JournalEntry> list = gson.fromJson(content, new TypeToken<List<JournalEntry>>() {
            }.getType());
            return list != null ? list : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error loading journal for " + username + ": " + e.getMessage());
            e.printStackTrace();
            try {
                if (file.exists()) {
                    file.renameTo(new File(DATA_DIR + "/journal_" + username + ".json.bak"));
                }
            } catch (Exception ex) {
                // Ignore backup errors
            }
            return new ArrayList<>();
        }
    }
}
