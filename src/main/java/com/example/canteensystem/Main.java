package com.example.canteensystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        CanteenService canteenService = new CanteenService();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        fxmlLoader.setController(new LoginController(canteenService, stage));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Canteen System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
