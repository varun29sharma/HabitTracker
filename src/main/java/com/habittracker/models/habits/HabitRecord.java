package com.habittracker.models.habits;

import java.time.LocalDate;

public class HabitRecord {
    private LocalDate date;
    private boolean completed;

    public HabitRecord(LocalDate date, boolean completed) {
        this.date = date;
        this.completed = completed;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
