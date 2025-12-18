package com.habittracker.models.journal;

import java.util.UUID;
import java.time.LocalDate;

public class JournalEntry {
    private UUID id;
    private LocalDate date;
    private String title;
    private String body;

    public JournalEntry(String title, String body) {
        this.id = UUID.randomUUID();
        this.date = LocalDate.now();
        this.title = title;
        this.body = body;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
