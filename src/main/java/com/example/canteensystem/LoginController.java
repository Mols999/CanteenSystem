package com.example.canteensystem;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField employeeIdField;

    private final CanteenService canteenService;
    private final Stage stage;

    public LoginController(CanteenService canteenService, Stage stage) {
        this.canteenService = canteenService;
        this.stage = stage;
    }

    @FXML
    private void handleLogin() {
        String employeeId = employeeIdField.getText();
        if (canteenService.isValidEmployee(employeeId)) {
            // Switch to the Transactions or Inventory Management screen
        } else {
            // Show an error message or clear the input field
        }
    }
}
