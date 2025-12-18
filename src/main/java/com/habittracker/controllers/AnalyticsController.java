package com.habittracker.controllers;

import com.habittracker.app.SceneManager;
import com.habittracker.models.habits.Habit;
import com.habittracker.models.habits.HabitRecord;
import com.habittracker.services.HabitService;
import com.habittracker.services.ThemeService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalyticsController {

    @FXML
    private Label longestStreakLabel;
    @FXML
    private Label mostCompletedLabel;
    @FXML
    private Label leastCompletedLabel;
    @FXML
    private Label habitsPerDayLabel;
    @FXML
    private LineChart<String, Number> trendChart;
    @FXML
    private GridPane heatmapGrid;

    private HabitService habitService = new HabitService();

    @FXML
    public void initialize() {
        populateStats();
        populateChart();
        populateHeatmap();
    }

    private void populateStats() {
        List<Habit> habits = habitService.getAllHabits();
        if (habits.isEmpty()) {
            longestStreakLabel.setText("0 Days");
            mostCompletedLabel.setText("No habits yet");
            leastCompletedLabel.setText("No habits yet");
            if (habitsPerDayLabel != null) {
                habitsPerDayLabel.setText("0 habits/day");
            }
            return;
        }

        Habit bestStreakHabit = null;
        Habit mostConsistent = null;
        Habit leastConsistent = null;
        int maxCompletions = -1;
        int minCompletions = Integer.MAX_VALUE;

        // Calculate average habits per day
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysAgo = today.minusDays(30);
        int totalDays = 0;
        int totalHabitsDone = 0;

        for (Habit h : habits) {
            // Update streaks
            habitService.calculateStreaks(h);
            
            if (bestStreakHabit == null || h.getLongestStreak() > bestStreakHabit.getLongestStreak()) {
                bestStreakHabit = h;
            }
            
            // Count completions in last 30 days
            int completions = 0;
            for (HabitRecord record : h.getRecords()) {
                if (record.isCompleted() && !record.getDate().isBefore(thirtyDaysAgo)) {
                    completions++;
                }
            }

            if (completions > maxCompletions) {
                maxCompletions = completions;
                mostConsistent = h;
            }
            if (completions < minCompletions || minCompletions == Integer.MAX_VALUE) {
                minCompletions = completions;
                leastConsistent = h;
            }
        }

        // Calculate average habits per day
        for (int i = 0; i < 30; i++) {
            LocalDate date = today.minusDays(i);
            int count = 0;
            for (Habit h : habits) {
                if (h.isCompleted(date)) {
                    count++;
                }
            }
            if (count > 0) {
                totalDays++;
            }
            totalHabitsDone += count;
        }

        if (bestStreakHabit != null) {
            longestStreakLabel.setText(bestStreakHabit.getLongestStreak() + " Days (" + bestStreakHabit.getName() + ")");
        }
        if (mostConsistent != null) {
            mostCompletedLabel.setText(mostConsistent.getName() + " (" + maxCompletions + " times)");
        }
        if (leastConsistent != null) {
            leastCompletedLabel.setText(leastConsistent.getName() + " (" + minCompletions + " times)");
        }
        if (habitsPerDayLabel != null) {
            double avgPerDay = totalDays > 0 ? (double) totalHabitsDone / 30 : 0;
            habitsPerDayLabel.setText(String.format("%.1f habits/day (last 30 days)", avgPerDay));
        }
    }

    private void populateChart() {
        trendChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Habits Completed Per Day");

        LocalDate today = LocalDate.now();
        List<Habit> habits = habitService.getAllHabits();

        for (int i = 29; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            int count = 0;
            for (Habit h : habits) {
                if (h.isCompleted(date)) {
                    count++;
                }
            }
            series.getData().add(new XYChart.Data<>(date.format(DateTimeFormatter.ofPattern("MM/dd")), count));
        }

        trendChart.getData().add(series);
    }

    private void populateHeatmap() {
        heatmapGrid.getChildren().clear();
        
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(364); // Last year
        List<Habit> habits = habitService.getAllHabits();

        // Create a map to store activity counts per date
        Map<LocalDate, Integer> activityMap = new HashMap<>();
        for (int i = 0; i < 365; i++) {
            LocalDate date = startDate.plusDays(i);
            if (date.isAfter(today)) break;
            
            int count = 0;
            for (Habit h : habits) {
                if (h.isCompleted(date)) {
                    count++;
                }
            }
            activityMap.put(date, count);
        }

        // Calculate week and day positions
        int currentWeek = 0;
        Map<Integer, Map<Integer, LocalDate>> weekMap = new HashMap<>();
        
        LocalDate current = startDate;
        while (!current.isAfter(today)) {
            int dayOfWeek = current.getDayOfWeek().getValue() - 1; // 0 = Monday, 6 = Sunday
            if (!weekMap.containsKey(currentWeek)) {
                weekMap.put(currentWeek, new HashMap<>());
            }
            weekMap.get(currentWeek).put(dayOfWeek, current);
            
            if (dayOfWeek == 6) { // Sunday, end of week
                currentWeek++;
            }
            current = current.plusDays(1);
        }

        // Add rectangles to grid
        for (int week = 0; week <= currentWeek; week++) {
            Map<Integer, LocalDate> weekDays = weekMap.get(week);
            if (weekDays == null) continue;
            
            for (int day = 0; day < 7; day++) {
                LocalDate date = weekDays.get(day);
                if (date == null || date.isAfter(today)) {
                    continue;
                }
                
                int count = activityMap.getOrDefault(date, 0);
                Rectangle rect = new Rectangle(12, 12);
                rect.setArcWidth(2);
                rect.setArcHeight(2);
                rect.setFill(getColorForCount(count));
                
                String tooltipText = String.format("%s: %d habit(s) completed", 
                    date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")), count);
                Tooltip.install(rect, new Tooltip(tooltipText));
                
                heatmapGrid.add(rect, week, day);
            }
        }
    }

    private Color getColorForCount(int count) {
        if (count == 0)
            return Color.rgb(235, 237, 240); // #ebedf0
        if (count <= 1)
            return Color.rgb(155, 233, 168); // #9be9a8
        if (count <= 3)
            return Color.rgb(64, 196, 99); // #40c463
        if (count <= 5)
            return Color.rgb(48, 161, 78); // #30a14e
        return Color.rgb(33, 110, 57); // #216e39
    }

    @FXML
    public void goBack() {
        SceneManager.switchScene("dashboard.fxml");
    }

    @FXML
    public void toggleTheme() {
        Scene scene = trendChart.getScene();
        if (scene != null) {
            ThemeService.toggleTheme(scene);
        }
    }
}
