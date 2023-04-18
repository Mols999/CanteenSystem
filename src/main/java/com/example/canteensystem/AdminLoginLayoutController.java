package com.example.canteensystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminLoginLayoutController {
    public AdminLoginLayoutController() {
    }
    private Main main;
    private Stage primaryStage;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    public void setMain(Main main) {
        this.main = main;
    }

    public AdminLoginLayoutController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public boolean isValidAdminCredentials(String username, String password) {
        boolean isValid = false;

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-2NQ6KUQ8;databaseName=dbCanteen;user=sa;password=1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Admin");

            while (resultSet.next()) {
                if (resultSet.getString("Fornavn").equals(username) && resultSet.getString("Password").equals(password)) {
                    isValid = true;
                    break;
                }
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }

    @FXML
    public void handleAdminLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidAdminCredentials(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/canteensystem/InventoryManagement.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) primaryStage.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Incorrect admin login credentials");
            errorLabel.setStyle("-fx-text-fill: red;");
        }
    }

}
