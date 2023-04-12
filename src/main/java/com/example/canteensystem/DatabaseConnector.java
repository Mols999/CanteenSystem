package com.example.canteensystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String CONNECTION_URL = "jdbc:sqlserver://localhost;databaseName=Canteen;user=your_username;password=your_password";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_URL);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
        return connection;
    }
}
