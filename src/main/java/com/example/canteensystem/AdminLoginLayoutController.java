package com.example.canteensystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class AdminLoginLayoutController {
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
    protected void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Admin loggedInAdmin = isValidCredentials(username, password);
        if (loggedInAdmin != null) {
            // Load the AdminLayout.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InventoryManagement.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }



            // Switch to the AdminLayout scene
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            errorLabel.setText("Incorrect login credentials");
            errorLabel.setStyle("-fx-text-fill: red;");
        }
    }


    private Admin isValidCredentials(String username, String password) {
        Admin admin = null;

        try {
            Connection con = DB.DatabaseConnector.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Admin");

            while (resultSet.next()) {
                if (resultSet.getString("AdminNummer").equals(username) && resultSet.getString("Password").equals(password)) {
                    admin = new Admin(
                            resultSet.getInt("AdminNummer"),
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

        return admin;
    }

}
