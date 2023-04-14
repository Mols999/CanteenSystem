package com.example.canteensystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainLayoutController {
    @FXML
    public void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        // Extract the product name and price from the button text
        String[] parts = buttonText.split(" - ");
        String productName = parts[0];
        double productPrice = Double.parseDouble(parts[1]);

        // Perform the desired action with the product name and price
        System.out.println("Product: " + productName + ", Price: " + productPrice);
    }
}