package com.habittracker.models.tasks;

import java.util.*;
import java.time.LocalDate;

public class Task {
    private String title;
    private String notes;
    private boolean completed;
    private LocalDate dateAssigned;
    private boolean struckOff;

    public Task(String title, String notes) {
        this.title = title;
        this.notes = notes;
        this.completed = false;
        this.dateAssigned = LocalDate.now();
        this.struckOff = false;
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDate getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(LocalDate dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isStruckOff() {
        return struckOff;
    }

    public void setStruckOff(boolean struckOff) {
        this.struckOff = struckOff;
    }
}
