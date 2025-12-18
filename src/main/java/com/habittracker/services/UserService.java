package com.habittracker.services;

import com.habittracker.models.user.User;
import java.util.List;
import java.util.Optional;

public class UserService {
    private List<User> users;
    private static User currentUser;

    public UserService() {
        this.users = DataStore.loadUsers();
    }

    public boolean register(String username, String password) {
        if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
            return false;
        }
        users.add(new User(username, password));
        DataStore.saveUsers(users);
        return true;
    }

    public boolean login(String username, String password) {
        Optional<User> match = users.stream()
                .filter(u -> u.getUsername().equals(username) && u.checkPassword(password))
                .findFirst();
        if (match.isPresent()) {
            currentUser = match.get();
            return true;
        }
        return false;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }
}
