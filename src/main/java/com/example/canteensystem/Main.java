package com.example.canteensystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    private int employeeId;

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        loadLoginLayout();


    }



    public class DatabaseConnectionTest {
        public static void main(String[] args) {
            try {
                Connection con = DB.DatabaseConnector.getConnection();

                if (con != null) {
                    System.out.println("Database connection successful!");
                } else {
                    System.out.println("Failed to establish a database connection.");
                }

                con.close();
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

}
