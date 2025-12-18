package com.habittracker.services;

import com.habittracker.models.habits.Frequency;
import com.habittracker.models.habits.Habit;
import com.habittracker.models.habits.HabitRecord;
import com.habittracker.models.user.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HabitService {

    public void addHabit(String name, Frequency frequency) {
        addHabit(name, frequency, "⭐");
    }

    public void addHabit(String name, Frequency frequency, String emoji) {
        User currentUser = UserService.getCurrentUser();
        if (currentUser == null)
            return;

        List<Habit> habits = DataStore.loadHabits(currentUser.getUsername());
        habits.add(new Habit(name, frequency, emoji));
        DataStore.saveHabits(currentUser.getUsername(), habits);
    }

    public List<Habit> getAllHabits() {
        User currentUser = UserService.getCurrentUser();
        if (currentUser == null)
            return new ArrayList<>();

        List<Habit> habits = DataStore.loadHabits(currentUser.getUsername());
        // Calculate streaks for all habits and ensure emoji is set
        for (Habit habit : habits) {
            // Ensure emoji is set (for backward compatibility)
            if (habit.getEmoji() == null || habit.getEmoji().isEmpty() || habit.getEmoji().equals("?")) {
                habit.setEmoji("⭐");
            }
            calculateStreaks(habit);
        }
        return habits;
    }

    public void saveState(List<Habit> habits) {
        User currentUser = UserService.getCurrentUser();
        if (currentUser == null)
            return;
        
        // Recalculate streaks before saving
        for (Habit habit : habits) {
            calculateStreaks(habit);
        }
        DataStore.saveHabits(currentUser.getUsername(), habits);
    }

    public void deleteHabit(Habit habit) {
        User currentUser = UserService.getCurrentUser();
        if (currentUser == null)
            return;
        
        List<Habit> habits = DataStore.loadHabits(currentUser.getUsername());
        // Remove by comparing name and frequency to ensure we remove the correct habit
        habits.removeIf(h -> h.getName().equals(habit.getName()) && 
                            h.getFrequency() == habit.getFrequency());
        DataStore.saveHabits(currentUser.getUsername(), habits);
    }

    public void calculateStreaks(Habit habit) {
        List<HabitRecord> records = habit.getRecords();
        if (records.isEmpty()) {
            habit.setCurrentStreak(0);
            habit.setLongestStreak(0);
            return;
        }

        // Sort records by date (oldest first)
        List<HabitRecord> sortedRecords = records.stream()
            .filter(r -> r.isCompleted())
            .sorted(Comparator.comparing(HabitRecord::getDate))
            .collect(Collectors.toList());

        if (sortedRecords.isEmpty()) {
            habit.setCurrentStreak(0);
            habit.setLongestStreak(0);
            return;
        }

        // Calculate longest streak
        int longestStreak = 1;
        int currentRun = 1;
        LocalDate prevDate = sortedRecords.get(0).getDate();
        
        for (int i = 1; i < sortedRecords.size(); i++) {
            LocalDate currDate = sortedRecords.get(i).getDate();
            if (prevDate.plusDays(1).equals(currDate)) {
                currentRun++;
            } else {
                longestStreak = Math.max(longestStreak, currentRun);
                currentRun = 1;
            }
            prevDate = currDate;
        }
        longestStreak = Math.max(longestStreak, currentRun);
        habit.setLongestStreak(longestStreak);

        // Calculate current streak (from today backwards)
        LocalDate today = LocalDate.now();
        int currentStreak = 0;
        LocalDate checkDate = today;
        
        // Check if today is completed
        boolean todayCompleted = habit.isCompleted(today);
        if (!todayCompleted) {
            checkDate = today.minusDays(1);
        }
        
        while (true) {
            boolean completed = habit.isCompleted(checkDate);
            if (completed) {
                currentStreak++;
                checkDate = checkDate.minusDays(1);
            } else {
                break;
            }
        }
        
        habit.setCurrentStreak(currentStreak);
    }

    public boolean shouldShowHabitToday(Habit habit, LocalDate date) {
        Frequency freq = habit.getFrequency();
        switch (freq) {
            case DAILY:
                return true;
            case WEEKLY:
                // For weekly, allow checking any day of the week
                // But we can restrict to specific day if needed
                // For now, allow any day for flexibility
                return true;
            case MONTHLY:
                // For monthly, allow checking any day of the month
                // But we can restrict to specific day if needed
                // For now, allow any day for flexibility
                return true;
            default:
                return true;
        }
    }
}
