package com.habittracker.models.user;

public class User {
    private String username;
    private String password; // In real app, hash this!

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String input) {
        return this.password.equals(input);
    }
}
