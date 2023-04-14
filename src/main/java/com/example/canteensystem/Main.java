package com.example.canteensystem;

import com.example.canteensystem.LoginLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        loadLoginLayout();
    }

    public class DatabaseConnectionTest {
        public static void main(String[] args) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-2NQ6KUQ8;databaseName=dbCanteen;user=sa;password=1234");

                if (connection != null) {
                    System.out.println("Database connection successful!");
                } else {
                    System.out.println("Failed to establish a database connection.");
                }

                connection.close();
            } catch (Exception e) {
                System.out.println("Error while connecting to the database:");
                e.printStackTrace();
            }
        }
    }

    private void loadLoginLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginLayout.fxml"));
        Parent root = loader.load();
        LoginLayoutController controller = loader.getController();
        controller.setMain(this);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void loadMainLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainLayout.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
    }
}
