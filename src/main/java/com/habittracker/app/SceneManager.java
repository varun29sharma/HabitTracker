package com.habittracker.app;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneManager {

    private static Stage primaryStage;

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(String fxmlFile) {
        try {
            String resourcePath = "/fxml/" + fxmlFile;
            System.out.println("Loading FXML: " + resourcePath);
            
            // Try multiple methods to load the resource
            java.net.URL resourceUrl = SceneManager.class.getResource(resourcePath);
            if (resourceUrl == null) {
                // Try with class loader
                resourceUrl = SceneManager.class.getClassLoader().getResource("fxml/" + fxmlFile);
            }
            if (resourceUrl == null) {
                throw new java.io.FileNotFoundException("FXML file not found: " + resourcePath + 
                    " (also tried: fxml/" + fxmlFile + ")");
            }
            
            System.out.println("Found FXML resource at: " + resourceUrl);
            
            FXMLLoader loader = new FXMLLoader(resourceUrl);
            
            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 700);

            // Apply theme
            com.habittracker.services.ThemeService.applyThemeToScene(scene);

            // If loading dashboard, refresh it
            if (fxmlFile.equals("dashboard.fxml")) {
                Object controller = loader.getController();
                if (controller instanceof com.habittracker.controllers.DashboardController) {
                    com.habittracker.controllers.DashboardController dashboardController = 
                        (com.habittracker.controllers.DashboardController) controller;
                    // Refresh dashboard data
                    javafx.application.Platform.runLater(() -> {
                        dashboardController.refresh();
                    });
                }
            }

            // Fade in animation
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();

            primaryStage.setScene(scene);
            System.out.println("Successfully loaded: " + fxmlFile);
        } catch (Exception e) {
            System.err.println("ERROR loading FXML: " + fxmlFile);
            e.printStackTrace();
            try (java.io.PrintWriter pw = new java.io.PrintWriter("error.log")) {
                e.printStackTrace(pw);
            } catch (java.io.FileNotFoundException ex) {
                ex.printStackTrace();
            }

            // Show error dialog
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Error Loading View");
            alert.setHeaderText("Failed to load " + fxmlFile);
            alert.setContentText("Error: " + e.getMessage() + "\n\nPlease ensure all FXML files are in src/main/resources/fxml/");
            alert.showAndWait();
        }
    }
}
