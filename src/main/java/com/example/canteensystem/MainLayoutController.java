
package com.example.canteensystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


public class MainLayoutController {
    @FXML
    private Label purchaseStatusLabel;
    private Medarbejder currentUser;
    private List<CartItem> cartItems = new ArrayList<>();



    public void initialize() {
        // Set the current user for testing purposes
        currentUser = getUserFromDatabase(1); // Replace 1 with the desired user ID
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        String buttonText = sourceButton.getText();
        String[] splitText = buttonText.split(" - ");
        String productName = splitText[0];
        double productPrice = Double.parseDouble(splitText[1].split(" ")[0]);

        Vare product;

        switch (productName) {
            case "Milk":
                product = new Vare(1, "Milk", 10.00, 50);
                break;
            case "Bottlewater":
                product = new Vare(2, "Bottle water", 10.00, 100);
                break;
            case "CocaCola":
                product = new Vare(3, "Coca Cola", 15.00, 65);
                break;
            case "Todaymeal":
                product = new Vare(4, "Today meal", 45.00, 200);
                break;
            case "Sandwich":
                product = new Vare(5, "Sandwich", 30.00, 50);
                break;
            case "Cake":
                product = new Vare(6, "Cake", 15.00, 50);
                break;
            case "Coffee":
                product = new Vare(7, "Coffee", 10.00, 300);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + productName);
        }

        CartItem cartItem = findCartItem(product);

        if (cartItem == null) {
            cartItems.add(new CartItem(product, 1));
        } else {
            cartItem.incrementQuantity();
        }
    }

    private CartItem findCartItem(Vare product) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getVare().equals(product)) {
                return cartItem;
            }
        }
        return null;
    }

    private double calculateTotalCost(List<CartItem> cartItems) {
        double totalCost = 0;
        for (CartItem cartItem : cartItems) {
            totalCost += cartItem.getVare().getPris() * cartItem.getQuantity();
        }
        return totalCost;
    }

    // Part 2
    @FXML
    private void handlePurchaseClick(ActionEvent event) {
        double totalCost = calculateTotalCost(cartItems);
        if (totalCost > 0) {
            if (currentUser.getPengePaaKonto() >= totalCost) {
                currentUser.setPengePaaKonto(currentUser.getPengePaaKonto() - totalCost);
                updateUserInDatabase(currentUser);

                for (CartItem cartItem : cartItems) {
                    Vare vare = cartItem.getVare();
                    vare.setAntal(vare.getAntal() - cartItem.getQuantity());
                    updateVareInDatabase(vare);
                }

                purchaseStatusLabel.setTextFill(Color.GREEN);
                purchaseStatusLabel.setText("Approved");
            } else {
                purchaseStatusLabel.setTextFill(Color.RED);
                purchaseStatusLabel.setText("Declined");
            }
        } else {
            purchaseStatusLabel.setTextFill(Color.ORANGE);
            purchaseStatusLabel.setText("Cart is empty");
        }
    }

    @FXML
    private void handleResetCartClick(ActionEvent event) {
        cartItems.clear();
        purchaseStatusLabel.setTextFill(Color.BLACK);
        purchaseStatusLabel.setText("Cart reset");
    }

    private Medarbejder getUserFromDatabase(int userId) {
        Medarbejder user = null;
        String query = "SELECT * FROM Medarbejder WHERE MedarbejderNummer = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-2NQ6KUQ8;databaseName=dbCanteen;user=sa;password=1234");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new Medarbejder(resultSet.getInt("MedarbejderNummer"),
                            resultSet.getString("Fornavn"),
                            resultSet.getString("Efternavn"),
                            resultSet.getDouble("PengePaaKonto"),
                            resultSet.getString("Password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private void updateUserInDatabase(Medarbejder user) {
        String query = "UPDATE Medarbejder SET PengePaaKonto = ? WHERE MedarbejderNummer = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-2NQ6KUQ8;databaseName=dbCanteen;user=sa;password=1234");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, user.getPengePaaKonto());
            preparedStatement.setInt(2, user.getMedarbejderNummer());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateVareInDatabase(Vare vare) {
        String query = "UPDATE Vare SET Antal = ? WHERE VareId = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-2NQ6KUQ8;databaseName=dbCanteen;user=sa;password=1234");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, vare.getAntal());
            preparedStatement.setInt(2, vare.getVareId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
