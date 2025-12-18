package com.habittracker.controllers;

import com.habittracker.models.journal.JournalEntry;
import com.habittracker.services.JournalService;
import com.habittracker.services.ThemeService;
import com.habittracker.app.SceneManager;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.util.Callback;

public class JournalController {

    @FXML
    private TextArea journalEntryArea;

    @FXML
    private ListView<JournalEntry> journalList;

    private JournalService service = new JournalService();

    @FXML
    public void initialize() {
        updateList();

        // Custom cell factory
        journalList.setCellFactory(new Callback<ListView<JournalEntry>, ListCell<JournalEntry>>() {
            @Override
            public ListCell<JournalEntry> call(ListView<JournalEntry> param) {
                return new ListCell<JournalEntry>() {
                    @Override
                    protected void updateItem(JournalEntry item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            // Show date and preview of content
                            String body = item.getBody();
                            String preview = body.length() > 50 ? body.substring(0, 50) + "..." : body;
                            setText(item.getDate() + ": " + preview);
                        }
                    }
                };
            }
        });
    }

    @FXML
    public void addEntry() {
        String content = journalEntryArea.getText();

        if (content != null && !content.trim().isEmpty()) {
            service.addEntry("Journal Entry", content);
            journalEntryArea.clear();
            updateList();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Entry");
            alert.setContentText("Please write something before saving.");
            alert.showAndWait();
        }
    }

    @FXML
    public void goBack() {
        SceneManager.switchScene("dashboard.fxml");
    }

    @FXML
    public void toggleTheme() {
        Scene scene = journalList.getScene();
        if (scene != null) {
            ThemeService.toggleTheme(scene);
        }
    }

    private void updateList() {
        journalList.setItems(FXCollections.observableArrayList(service.getAllEntries()));
    }

    // Original methods kept for compatibility
    public void deleteEntry(JournalEntry entry) {
        service.deleteEntry(entry);
    }
}
