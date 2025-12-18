package com.habittracker.controllers;

import com.habittracker.models.habits.Frequency;
import com.habittracker.models.habits.Habit;
import com.habittracker.services.HabitService;
import com.habittracker.services.ThemeService;
import com.habittracker.app.SceneManager;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HabitController {

    @FXML
    private TextField habitNameField;

    @FXML
    private TextField emojiField;

    @FXML
    private ComboBox<Frequency> frequencyComboBox;

    @FXML
    private VBox habitsContainer;

    @FXML
    private VBox emptyStateBox;

    @FXML
    private Label emptyStateLabel;

    @FXML
    private ScrollPane scrollPane;

    private HabitService service = new HabitService();
    private static final String[] DAY_NAMES = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    @FXML
    public void initialize() {
        frequencyComboBox.setItems(FXCollections.observableArrayList(Frequency.values()));
        if (emojiField != null) {
            emojiField.setPromptText("e.g., 🏃 💪 📚");
        }
        updateHabitsDisplay();
    }

    @FXML
    public void addHabit() {
        String name = habitNameField.getText();
        Frequency freq = frequencyComboBox.getValue();
        String emoji = emojiField != null ? emojiField.getText().trim() : "⭐";

        if (name != null && !name.trim().isEmpty() && freq != null) {
            if (emoji.isEmpty()) {
                emoji = "⭐";
            }
            service.addHabit(name, freq, emoji);
            habitNameField.clear();
            if (emojiField != null) {
                emojiField.clear();
            }
            frequencyComboBox.getSelectionModel().clearSelection();
            updateHabitsDisplay();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please enter a name and select a frequency.");
            alert.showAndWait();
        }
    }

    @FXML
    public void goBack() {
        // Refresh will happen automatically in SceneManager when loading dashboard
        SceneManager.switchScene("dashboard.fxml");
    }

    @FXML
    public void toggleTheme() {
        Scene scene = habitsContainer != null ? habitsContainer.getScene() : null;
        if (scene != null) {
            ThemeService.toggleTheme(scene);
        }
    }

    private void updateHabitsDisplay() {
        if (habitsContainer == null) {
            System.err.println("ERROR: habitsContainer is null!");
            return;
        }

        try {
            List<Habit> habits = service.getAllHabits();
            System.out.println("Loaded " + habits.size() + " habits");
            habitsContainer.getChildren().clear();

            if (habits.isEmpty()) {
                if (emptyStateBox != null) {
                    emptyStateBox.setVisible(true);
                    emptyStateBox.setManaged(true);
                }
                return;
            }

            if (emptyStateBox != null) {
                emptyStateBox.setVisible(false);
                emptyStateBox.setManaged(false);
            }

        // Get current week (Monday to Sunday)
        LocalDate today = LocalDate.now();
        LocalDate monday = today;
        // Find the Monday of the current week
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }

        // Create header with day names
        HBox headerBox = new HBox(5);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(10, 0, 10, 0));
        
        // Empty cell for habit name column (with space for delete button)
        Region spacer = new Region();
        spacer.setPrefWidth(250);
        headerBox.getChildren().add(spacer);

        // Add day headers
        for (int i = 0; i < 7; i++) {
            LocalDate day = monday.plusDays(i);
            VBox dayHeader = new VBox(5);
            dayHeader.setAlignment(Pos.CENTER);
            dayHeader.setPrefWidth(80);
            
            Label dayNameLabel = new Label(DAY_NAMES[i]);
            dayNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
            
            Label dateLabel = new Label(day.format(DateTimeFormatter.ofPattern("MM/dd")));
            dateLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: -secondary-text;");
            
            // Highlight today
            if (day.equals(today)) {
                dayHeader.setStyle("-fx-background-color: -accent-color; -fx-background-radius: 8; -fx-padding: 5;");
                dayNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: white;");
                dateLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: rgba(255,255,255,0.9);");
            }
            
            dayHeader.getChildren().addAll(dayNameLabel, dateLabel);
            headerBox.getChildren().add(dayHeader);
        }

        habitsContainer.getChildren().add(headerBox);

            // Create a row for each habit
            for (Habit habit : habits) {
                System.out.println("Creating row for habit: " + habit.getName() + " with emoji: " + habit.getEmoji());
                HBox habitRow = createHabitRow(habit, monday, today);
                habitsContainer.getChildren().add(habitRow);
            }
        } catch (Exception e) {
            System.err.println("Error updating habits display: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private HBox createHabitRow(Habit habit, LocalDate weekStart, LocalDate today) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(15));
        row.setSpacing(10);
        row.setStyle("-fx-background-color: -card-bg; -fx-background-radius: 8; -fx-padding: 15;");

        // Habit name and emoji column with delete button
        HBox habitInfo = new HBox(15);
        habitInfo.setAlignment(Pos.CENTER_LEFT);
        habitInfo.setPrefWidth(250);
        habitInfo.setPadding(new Insets(0, 10, 0, 10));
        
        String emoji = habit.getEmoji() != null && !habit.getEmoji().isEmpty() ? habit.getEmoji() : "⭐";
        Label emojiLabel = new Label(emoji);
        emojiLabel.setStyle("-fx-font-size: 28px; -fx-padding: 0 5 0 0;");
        
        VBox nameBox = new VBox(5);
        nameBox.setAlignment(Pos.CENTER_LEFT);
        String habitName = habit.getName() != null ? habit.getName() : "Unnamed Habit";
        Label nameLabel = new Label(habitName);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: -text-color;");
        
        Label streakLabel = new Label("🔥 " + habit.getCurrentStreak() + " day streak");
        streakLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #F59E0B;");
        
        nameBox.getChildren().addAll(nameLabel, streakLabel);
        
        // Delete button
        Button deleteButton = new Button("🗑️");
        deleteButton.setStyle("-fx-background-color: transparent; -fx-font-size: 16px; -fx-padding: 5; -fx-cursor: hand;");
        deleteButton.setOnAction(e -> {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Delete Habit");
            confirmAlert.setHeaderText("Delete Habit?");
            confirmAlert.setContentText("Are you sure you want to delete \"" + habit.getName() + "\"? This action cannot be undone.");
            
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        service.deleteHabit(habit);
                        // Immediately refresh the display
                        updateHabitsDisplay();
                        System.out.println("Habit deleted successfully");
                    } catch (Exception ex) {
                        System.err.println("Error deleting habit: " + ex.getMessage());
                        ex.printStackTrace();
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setContentText("Failed to delete habit. Please try again.");
                        errorAlert.showAndWait();
                    }
                }
            });
        });
        
        deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: #EF4444; -fx-background-radius: 4; -fx-font-size: 16px; -fx-padding: 5; -fx-cursor: hand; -fx-text-fill: white;"));
        deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: transparent; -fx-font-size: 16px; -fx-padding: 5; -fx-cursor: hand;"));
        
        habitInfo.getChildren().addAll(emojiLabel, nameBox, deleteButton);
        row.getChildren().add(habitInfo);

        // Add checkboxes for each day of the week with emoji
        for (int i = 0; i < 7; i++) {
            LocalDate day = weekStart.plusDays(i);
            VBox dayBox = new VBox(8);
            dayBox.setAlignment(Pos.CENTER);
            dayBox.setPrefWidth(80);
            dayBox.setPadding(new Insets(5));
            
            // Show emoji for this habit
            String dayEmoji = habit.getEmoji() != null && !habit.getEmoji().isEmpty() ? habit.getEmoji() : "⭐";
            Label emojiDayLabel = new Label(dayEmoji);
            emojiDayLabel.setStyle("-fx-font-size: 24px;");
            
            CheckBox dayCheckbox = new CheckBox();
            dayCheckbox.setAlignment(Pos.CENTER);
            dayCheckbox.setPrefWidth(80);
            dayCheckbox.setMouseTransparent(false);
            dayCheckbox.setFocusTraversable(true);
            
            // Set checked state
            boolean isCompleted = habit.isCompleted(day);
            dayCheckbox.setSelected(isCompleted);
            
            // Disable if it's a future date
            if (day.isAfter(today)) {
                dayCheckbox.setDisable(true);
                dayCheckbox.setOpacity(0.4);
                emojiDayLabel.setOpacity(0.4);
            } else {
                // Ensure checkbox is enabled for past and current dates
                dayCheckbox.setDisable(false);
            }
            
            // Handle checkbox change - use a final variable for the day
            final LocalDate finalDay = day;
            final String targetHabitName = habit.getName();
            final Frequency targetHabitFrequency = habit.getFrequency();
            dayCheckbox.setOnAction(e -> {
                try {
                    boolean newState = dayCheckbox.isSelected();
                    // Get the current list of habits from storage
                    List<Habit> allHabits = service.getAllHabits();
                    // Find the matching habit in the list
                    Habit habitToUpdate = null;
                    for (Habit h : allHabits) {
                        if (h.getName().equals(targetHabitName) && h.getFrequency() == targetHabitFrequency) {
                            habitToUpdate = h;
                            break;
                        }
                    }
                    // Update the habit if found
                    if (habitToUpdate != null) {
                        habitToUpdate.setCompleted(finalDay, newState);
                        // Save the updated list
                        service.saveState(allHabits);
                        System.out.println("Habit updated: " + targetHabitName + " for date " + finalDay + " = " + newState);
                    } else {
                        System.err.println("ERROR: Could not find habit to update: " + targetHabitName);
                    }
                    // Refresh display after a short delay to ensure save completes
                    javafx.application.Platform.runLater(() -> {
                        updateHabitsDisplay();
                    });
                } catch (Exception ex) {
                    System.err.println("Error updating habit: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
            
            // Style based on completion
            if (isCompleted) {
                dayCheckbox.setStyle("-fx-text-fill: -success-color;");
                emojiDayLabel.setStyle("-fx-font-size: 24px; -fx-opacity: 1.0;");
            } else {
                emojiDayLabel.setStyle("-fx-font-size: 24px; -fx-opacity: 0.5;");
            }
            
            // Ensure the dayBox doesn't block mouse events
            dayBox.setMouseTransparent(false);
            dayBox.getChildren().addAll(emojiDayLabel, dayCheckbox);
            row.getChildren().add(dayBox);
        }

        return row;
    }
}
