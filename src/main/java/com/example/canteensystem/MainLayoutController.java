package com.example.canteensystem;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainLayoutController {

    @FXML
    private Button exampleButton;

    @FXML
    private Label exampleLabel;

    @FXML
    public void initialize() {
        // Initialize your controller here if needed.
    }

    @FXML
    private void handleExampleButtonAction() {
        exampleLabel.setText("Example button clicked!");
    }
}
