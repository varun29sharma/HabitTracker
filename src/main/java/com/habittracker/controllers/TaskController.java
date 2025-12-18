package com.habittracker.controllers;

import com.habittracker.models.tasks.Task;
import com.habittracker.services.TaskService;
import com.habittracker.services.ThemeService;
import com.habittracker.app.SceneManager;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.time.LocalDate;

public class TaskController {

    @FXML
    private TextField taskTitleField;

    @FXML
    private TextField taskNotesField;

    @FXML
    private ListView<Task> taskList;

    @FXML
    private Label completionMessageLabel;

    private TaskService service = new TaskService();

    @FXML
    public void initialize() {
        updateList();
        showCompletionMessage();

        // Custom cell factory - ensure checkbox is fully interactive
        taskList.setCellFactory(listView -> {
            ListCell<Task> cell = new ListCell<Task>() {
                private final CheckBox checkBox = new CheckBox();
                private final Label titleLabel = new Label();
                private final HBox hbox = new HBox(10);
                
                {
                    hbox.getChildren().addAll(checkBox, titleLabel);
                    hbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                    hbox.setPadding(new javafx.geometry.Insets(5, 10, 5, 10));
                    
                    // Critical: Make sure the cell doesn't block mouse events
                    setMouseTransparent(false);
                    
                    // Set up checkbox handler with proper event handling
                    checkBox.setOnAction(event -> {
                        event.consume();
                        Task task = getItem();
                        if (task != null) {
                            boolean newState = checkBox.isSelected();
                            System.out.println("Checkbox action fired! Task: " + task.getTitle() + ", State: " + newState);
                            task.setCompleted(newState);
                            
                            javafx.collections.ObservableList<Task> items = taskList.getItems();
                            service.saveState(items);
                            showCompletionMessage();
                            
                            // Update styling directly
                            updateLabelStyle(task);
                        }
                    });
                    
                    // Also handle mouse clicks directly
                    checkBox.setOnMouseClicked(event -> {
                        event.consume();
                        Task task = getItem();
                        if (task != null) {
                            System.out.println("Checkbox mouse clicked! Task: " + task.getTitle());
                            checkBox.setSelected(!checkBox.isSelected());
                            checkBox.fire(); // Trigger the action event
                        }
                    });
                    
                    // Ensure checkbox is fully interactive
                    checkBox.setMouseTransparent(false);
                    checkBox.setFocusTraversable(true);
                    checkBox.setDisable(false);
                }
                
                private void updateLabelStyle(Task task) {
                    if (task.isStruckOff() && !task.isCompleted()) {
                        titleLabel.setStyle("-fx-strikethrough: true; -fx-text-fill: #94A3B8;");
                    } else if (task.isCompleted()) {
                        titleLabel.setStyle("-fx-strikethrough: false; -fx-text-fill: #10B981; -fx-font-weight: bold;");
                    } else {
                        titleLabel.setStyle("-fx-strikethrough: false; -fx-text-fill: -text-color;");
                    }
                }

                @Override
                protected void updateItem(Task task, boolean empty) {
                    super.updateItem(task, empty);
                    
                    if (empty || task == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        // Update checkbox state without triggering event
                        boolean wasSelected = checkBox.isSelected();
                        checkBox.setSelected(task.isCompleted());
                        checkBox.setDisable(false);
                        checkBox.setMouseTransparent(false);
                        
                        // Update label
                        titleLabel.setText(task.getTitle());
                        updateLabelStyle(task);
                        
                        setGraphic(hbox);
                        setText(null);
                    }
                }
            };
            
            // Ensure cell doesn't block events
            cell.setMouseTransparent(false);
            return cell;
        });
    }

    @FXML
    public void addTask() {
        String title = taskTitleField.getText();
        String notes = taskNotesField.getText();

        if (title != null && !title.trim().isEmpty()) {
            service.addTask(title, notes);
            taskTitleField.clear();
            taskNotesField.clear();
            updateList();
            showCompletionMessage();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please enter a task title.");
            alert.showAndWait();
        }
    }

    @FXML
    public void goBack() {
        SceneManager.switchScene("dashboard.fxml");
    }

    @FXML
    public void toggleTheme() {
        Scene scene = taskList.getScene();
        if (scene != null) {
            ThemeService.toggleTheme(scene);
        }
    }

    private void updateList() {
        taskList.setItems(FXCollections.observableArrayList(service.getTodayTasks()));
    }

    private void showCompletionMessage() {
        if (completionMessageLabel == null) return;
        
        TaskService.TaskCompletionStats stats = service.getCompletionStats();
        int completed = stats.getCompleted();
        int leftOut = stats.getLeftOut();
        
        if (completed > 0 || leftOut > 0) {
            String message = String.format("Today: Completed %d task(s), Left out %d task(s)", 
                completed, leftOut);
            completionMessageLabel.setText(message);
            if (leftOut > 0) {
                completionMessageLabel.setStyle("-fx-text-fill: #F59E0B; -fx-font-size: 14px;");
            } else {
                completionMessageLabel.setStyle("-fx-text-fill: #10B981; -fx-font-size: 14px;");
            }
            completionMessageLabel.setVisible(true);
        } else {
            completionMessageLabel.setVisible(false);
        }
    }
}
