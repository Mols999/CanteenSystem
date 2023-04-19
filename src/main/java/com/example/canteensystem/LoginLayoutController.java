package com.example.canteensystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginLayoutController {
    private Main main;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text actionTarget;

    @FXML
    private Button handleGoToAdminLayout;

    @FXML
    private Label errorLabel;


    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    protected void handleSubmitButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Medarbejder loggedInUser = isValidCredentials(username, password);
        if (loggedInUser != null) {
            // Load the ShoppingGUI FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainLayout.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Get the ShoppingGUIController from the loader
            ShoppingGUIController shoppingGUIController = loader.getController();

            // Set the loggedInUser in the ShoppingGUIController
            shoppingGUIController.setLoggedInUser(loggedInUser);

            // Switch to the ShoppingGUI scene
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            errorLabel.setText("Incorrect login credentials");
            errorLabel.setStyle("-fx-text-fill: red;");
        }
    }


    private Medarbejder isValidCredentials(String username, String password) {
        Medarbejder medarbejder = null;

        try {
            Connection con = DB.DatabaseConnector.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Medarbejder");

            while (resultSet.next()) {
                if (resultSet.getString("MedarbejderNummer").equals(username) && resultSet.getString("Password").equals(password)) {
                    medarbejder = new Medarbejder(
                            resultSet.getInt("MedarbejderNummer"),
                            resultSet.getString("Fornavn"),
                            resultSet.getString("Efternavn"),
                            resultSet.getDouble("PengePaaKonto"),
                            resultSet.getString("Password")
                    );
                    break;
                }
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return medarbejder;
    }

    @FXML
    public void handleGoToAdminLayout(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/canteensystem/AdminLayout.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

