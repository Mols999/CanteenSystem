package com.example.canteensystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private final CanteenService canteenService;

    public Main() {
        // Initialize the CanteenService instance
        canteenService = new CanteenService();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/canteensystem/login.fxml"));
            loader.setControllerFactory(param -> new LoginController(canteenService, primaryStage));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void showInventoryManagementScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("inventory_management.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
