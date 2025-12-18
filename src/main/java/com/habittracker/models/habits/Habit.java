package com.habittracker.models.habits;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Habit {
    private String name;
    private Frequency frequency;
    private Date startDate;
    private List<HabitRecord> records;
    private int currentStreak;
    private int longestStreak;
    private String emoji;

    public Habit(String name, Frequency frequency) {
        this.name = name;
        this.frequency = frequency;
        this.startDate = new Date();
        this.records = new ArrayList<>();
        this.currentStreak = 0;
        this.longestStreak = 0;
        this.emoji = "⭐"; // Default emoji
    }

    public Habit(String name, Frequency frequency, String emoji) {
        this.name = name;
        this.frequency = frequency;
        this.startDate = new Date();
        this.records = new ArrayList<>();
        this.currentStreak = 0;
        this.longestStreak = 0;
        this.emoji = emoji != null ? emoji : "⭐";
    }

    public String getName() {
        return name;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public List<HabitRecord> getRecords() {
        return records;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public String getEmoji() {
        return emoji != null ? emoji : "⭐";
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji != null ? emoji : "⭐";
    }

    public boolean isCompleted(java.time.LocalDate date) {
        for (HabitRecord r : records) {
            if (r.getDate().equals(date)) {
                return r.isCompleted();
            }
        }
        return false;
    }

    public void setCompleted(java.time.LocalDate date, boolean completed) {
        boolean found = false;
        for (HabitRecord r : records) {
            if (r.getDate().equals(date)) {
                r.setCompleted(completed);
                found = true;
                break;
            }
        }
        if (!found) {
            records.add(new HabitRecord(date, completed));
        }
    }
}