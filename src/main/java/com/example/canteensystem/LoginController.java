package com.example.canteensystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button handleLogin;

    private final CanteenService canteenService;
    private final Stage stage;

    public LoginController(CanteenService canteenService, Stage stage) {
        this.canteenService = canteenService;
        this.stage = stage;
    }


    @FXML
    void handleLogin(ActionEvent event) {
        // Validate the entered ID and password
        String username = usernameField.getText();
        String password = passwordField.getText();


        if (isValidCredentials(username, password)) {
            // If valid, proceed to the Inventory Management screen
            Main.showInventoryManagementScreen();
        } else {
            // If invalid, display an error message or handle it as necessary
            System.out.println("Invalid ID or password.");
        }
    }

    private boolean isValidCredentials(String id, String password) {
        // Implement your logic to validate the entered ID and password
        // For example, you can check the ID and password against the database
        return true;
    }

}