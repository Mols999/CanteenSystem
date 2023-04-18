package com.example.canteensystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class ShoppingGUIController {
    private Stage primaryStage;

    Main main = new Main();

    @FXML
    private ListView<CartEntry> cartList;

    @FXML
    private Label totalPriceLabel;
    @FXML
    private Label purchaseStatusLabel;

    private Medarbejder loggedInUser;
    private Purchase purchase;
    private CartEntry.Cart cart;



    public void initialize() {
        try {
            String dbUrl = "jdbc:sqlserver://LAPTOP-2NQ6KUQ8;databaseName=dbCanteen";
            String user = "sa";
            String password = "1234";
            purchase = new Purchase(dbUrl, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        cart = new CartEntry.Cart();

        cartList.setItems(cart.getCartEntries());

        setupCartListCellFactory();
    }

    public void setLoggedInUser(Medarbejder loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


    public void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        CartEntry clickedEntry = getCartEntryFromButton(clickedButton);

        int index = cartList.getItems().indexOf(clickedEntry);
        if (index != -1) {
            CartEntry existingEntry = cartList.getItems().get(index);
            existingEntry.setQuantity(existingEntry.getQuantity() + 1);
            cartList.getItems().set(index, existingEntry);
        } else {
            clickedEntry.setQuantity(1);
            cartList.getItems().add(clickedEntry);
        }
        updateTotalPrice();
    }

    private CartEntry getCartEntryFromButton(Button button) {
        String buttonText = button.getText();
        String[] splitText = buttonText.split(" - ");
        String productName = splitText[0].trim();
        double productPrice = Double.parseDouble(splitText[1].replace(" kr", "").trim());

        int productId = getProductIdByName(productName);

        return new CartEntry(productId, productName, productPrice);
    }


    public void handlePurchaseClick() {
        double totalPrice = cartList.getItems().stream().mapToDouble(entry -> entry.getPrice() * entry.getQuantity()).sum();

        if (loggedInUser.getPengePaaKonto() >= totalPrice) {
            try {
                for (CartEntry entry : cartList.getItems()) {
                    purchase.makePurchase(loggedInUser.getMedarbejderNummer(), entry.getProductId(), entry.getQuantity());

                    // Update product stock
                    Vare vare = new Vare(entry.getProductId(), entry.getProductName(), entry.getPrice(), entry.getQuantity());
                    vare.updateProductStock(entry.getQuantity());
                }

                loggedInUser.setPengePaaKonto(loggedInUser.getPengePaaKonto() - totalPrice);
                loggedInUser.updateBalanceInDatabase(); // Add this line
                cartList.getItems().clear();
                purchaseStatusLabel.setText("Purchase successful!");
            } catch (SQLException e) {
                purchaseStatusLabel.setText("Purchase failed! Error: " + e.getMessage());
            }
        } else {
            purchaseStatusLabel.setText("Insufficient balance!");
        }
    }

    @FXML
    private void handleResetCartClick(ActionEvent event) {
        cart.clear();
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = cart.getCartEntries().stream().mapToDouble(entry -> entry.getPrice() * entry.getQuantity()).sum();
        totalPriceLabel.setText("Total: " + String.format("%.2f", totalPrice) + " kr");
    }

    private int getProductIdByName(String productName) {
        return 0;
    }

    private void setupCartListCellFactory() {
        cartList.setCellFactory(param -> new ListCell<CartEntry>() {
            @Override
            protected void updateItem(CartEntry item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getProductName() + " x" + item.getQuantity() + " - " + String.format("%.2f", item.getPrice() * item.getQuantity()) + " kr");
                }
            }
        });
    }
    @FXML
    public void handleGoToAdminLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLayout.fxml"));
            Parent root = loader.load();
            AdminLoginLayoutController controller = loader.getController();
            controller.setMain(main);
            Stage adminStage = new Stage();
            adminStage.setScene(new Scene(root));
            adminStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

