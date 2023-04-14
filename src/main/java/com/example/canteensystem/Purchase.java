package com.example.canteensystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Purchase {
    private Connection connection;

    public Purchase(String dbUrl, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(dbUrl, user, password);
    }

    public boolean makePurchase(int medarbejderNummer, int vareId, int quantity) throws SQLException {
        String updateVareQuery = "UPDATE Vare SET Antal = Antal - ? WHERE VareId = ?";
        String insertPurchaseQuery = "INSERT INTO Purchase (MedarbejderNummer, VareId, Antal, PurchaseTimestamp) VALUES (?, ?, ?, ?)";

        try (PreparedStatement updateVareStmt = connection.prepareStatement(updateVareQuery);
             PreparedStatement insertPurchaseStmt = connection.prepareStatement(insertPurchaseQuery)) {

            connection.setAutoCommit(false);

            updateVareStmt.setInt(1, quantity);
            updateVareStmt.setInt(2, vareId);
            int updatedRows = updateVareStmt.executeUpdate();

            if (updatedRows == 0) {
                connection.rollback();
                return false;
            }

            insertPurchaseStmt.setInt(1, medarbejderNummer);
            insertPurchaseStmt.setInt(2, vareId);
            insertPurchaseStmt.setInt(3, quantity);
            insertPurchaseStmt.setTimestamp(4, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            insertPurchaseStmt.executeUpdate();

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
