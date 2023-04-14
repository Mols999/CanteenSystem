package com.example.canteensystem;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.sql.Connection;
import java.sql.DriverManager;
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

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    protected void handleSubmitButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidCredentials(username, password)) {
            try {
                main.loadMainLayout();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            actionTarget.setText("Invalid credentials");
        }
    }

    private boolean isValidCredentials(String username, String password) {
        boolean validCredentials = false;

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-2NQ6KUQ8;databaseName=dbCanteen;user=sa;password=1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MedarbejderNummer, Password FROM Medarbejder");

            while (resultSet.next()) {
                if (resultSet.getString("MedarbejderNummer").equals(username) && resultSet.getString("Password").equals(password)) {
                    validCredentials = true;
                    break;
                }
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return validCredentials;
    }
}
