package com.habittracker.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.setStage(stage);
        SceneManager.switchScene("login.fxml");
        stage.setTitle("Habit Tracker");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
