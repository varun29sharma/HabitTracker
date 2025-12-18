package com.habittracker.controllers;

import com.habittracker.app.SceneManager;
import com.habittracker.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label headerLabel;
    @FXML
    private Button actionButton;
    @FXML
    private Hyperlink toggleLink;

    private UserService userService;
    private boolean isLoginMode = true;

    public void initialize() {
        userService = new UserService();
    }

    @FXML
    private void handleAction() {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        if (user.isEmpty() || pass.isEmpty()) {
            errorLabel.setText("Please fill all fields");
            return;
        }

        if (isLoginMode) {
            if (userService.login(user, pass)) {
                SceneManager.switchScene("dashboard.fxml");
            } else {
                errorLabel.setText("Invalid credentials");
                errorLabel.setStyle("-fx-text-fill: #EF4444;");
            }
        } else {
            if (userService.register(user, pass)) {
                // Determine success, maybe auto-login or ask to login
                // For safety, ask to login
                toggleMode();
                errorLabel.setText("Account created! Please sign in.");
                errorLabel.setStyle("-fx-text-fill: #10B981;"); // Green
            } else {
                errorLabel.setText("Username already exists");
                errorLabel.setStyle("-fx-text-fill: #EF4444;");
            }
        }
    }

    @FXML
    private void toggleMode() {
        isLoginMode = !isLoginMode;
        headerLabel.setText(isLoginMode ? "Welcome Back" : "Create Account");
        actionButton.setText(isLoginMode ? "Sign In" : "Sign Up");
        toggleLink.setText(isLoginMode ? "Don't have an account? Sign Up" : "Already have an account? Sign In");
        errorLabel.setText("");
        // Reset error style to red just in case
        errorLabel.setStyle("-fx-text-fill: #EF4444;");
    }
}
