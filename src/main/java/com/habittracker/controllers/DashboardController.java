package com.habittracker.controllers;

import com.habittracker.app.SceneManager;
import com.habittracker.services.HabitService;
import com.habittracker.services.TaskService;
import com.habittracker.services.JournalService;
import com.habittracker.services.ThemeService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Label habitCountLabel;

    @FXML
    private Label taskCountLabel;

    @FXML
    private Label journalCountLabel;

    @FXML
    private Label motivationalMessageLabel;

    @FXML
    private Label tasksMessageLabel;

    @FXML
    private Label habitsMessageLabel;

    @FXML
    private Label habitsCompletedLabel;

    @FXML
    private Label tasksCompletedLabel;

    private HabitService habitService = new HabitService();
    private TaskService taskService = new TaskService();
    private JournalService journalService = new JournalService();

    @FXML
    public void initialize() {
        updateCounts();
        updateMotivationalMessages();
    }

    private void updateCounts() {
        int habitCount = habitService.getAllHabits().size();
        if (habitCountLabel != null) {
            habitCountLabel.setText(String.valueOf(habitCount));
        }

        int habitsCompletedToday = (int) habitService.getAllHabits().stream()
            .filter(h -> h.isCompleted(java.time.LocalDate.now()))
            .count();
        if (habitsCompletedLabel != null) {
            habitsCompletedLabel.setText(habitsCompletedToday + " completed today");
        }

        int taskCount = taskService.getTodayTasks().size();
        if (taskCountLabel != null) {
            taskCountLabel.setText(String.valueOf(taskCount));
        }

        long completedTasks = taskService.getTodayTasks().stream()
            .filter(t -> t.isCompleted()).count();
        if (tasksCompletedLabel != null) {
            tasksCompletedLabel.setText(completedTasks + " of " + taskCount + " completed");
        }

        if (journalCountLabel != null) {
            journalCountLabel.setText(String.valueOf(journalService.getAllEntries().size()));
        }
    }

    private void updateMotivationalMessages() {
        int habitCount = habitService.getAllHabits().size();
        int taskCount = taskService.getTodayTasks().size();
        long incompleteTasks = taskService.getTodayTasks().stream()
            .filter(t -> !t.isCompleted()).count();
        long completedTasks = taskService.getTodayTasks().stream()
            .filter(t -> t.isCompleted()).count();
        int habitsCompletedToday = (int) habitService.getAllHabits().stream()
            .filter(h -> h.isCompleted(java.time.LocalDate.now()))
            .count();

        // Motivational message for habits
        if (motivationalMessageLabel != null) {
            if (habitCount == 0) {
                motivationalMessageLabel.setText("🌟 Start your journey! Add your first habit to begin tracking!");
            } else if (habitsCompletedToday == habitCount && habitCount > 0) {
                motivationalMessageLabel.setText("🎉 Amazing! You've completed all your habits today! Keep it up! 🔥");
            } else {
                motivationalMessageLabel.setText("💪 Keep going! You're building amazing habits!");
            }
        }

        // Task message
        if (tasksMessageLabel != null) {
            if (taskCount == 0) {
                tasksMessageLabel.setText("✨ No tasks for today! Great job staying organized!");
            } else if (incompleteTasks > 0) {
                tasksMessageLabel.setText("📝 You have " + incompleteTasks + " task(s) to complete today. Come on, you've got this! 💪");
            } else {
                tasksMessageLabel.setText("🎉 Amazing! All tasks completed! You're on fire! 🔥");
            }
        }

        // Habits message
        if (habitsMessageLabel != null) {
            if (habitCount == 0) {
                habitsMessageLabel.setText("➕ Add your first habit to start tracking your progress!");
            } else if (habitsCompletedToday == 0) {
                habitsMessageLabel.setText("⏰ Don't forget to check off your habits for today!");
            } else if (habitsCompletedToday < habitCount) {
                habitsMessageLabel.setText("📊 You've completed " + habitsCompletedToday + " of " + habitCount + " habits today. Keep going!");
            } else {
                habitsMessageLabel.setText("🌟 Perfect! All habits completed today! You're unstoppable!");
            }
        }
    }

    @FXML
    public void openHabits() {
        SceneManager.switchScene("habits.fxml");
    }

    @FXML
    public void openTasks() {
        SceneManager.switchScene("tasks.fxml");
    }

    @FXML
    public void openJournal() {
        SceneManager.switchScene("journal.fxml");
    }

    @FXML
    public void openAnalytics() {
        SceneManager.switchScene("analytics.fxml");
    }

    @FXML
    public void toggleTheme() {
        Scene scene = habitCountLabel != null ? habitCountLabel.getScene() : null;
        if (scene != null) {
            ThemeService.toggleTheme(scene);
        }
    }

    // Public method to refresh dashboard data
    public void refresh() {
        updateCounts();
        updateMotivationalMessages();
    }
}
