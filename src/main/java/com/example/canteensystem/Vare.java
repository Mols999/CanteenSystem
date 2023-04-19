package com.example.canteensystem;

import java.sql.Connection;
import java.sql.Statement;

public class Vare {
    private int vareId;
    private String navn;
    private double pris;
    private int antal;

    public Vare(int vareId, String navn, double pris, int antal) {
        this.vareId = vareId;
        this.navn = navn;
        this.pris = pris;
        this.antal = antal;
    }


    public boolean updateProductStock(int purchasedQuantity) {
        boolean success = false;

        try {
            Connection con = DB.DatabaseConnector.getConnection();
            Statement statement = con.createStatement();

            String updateStockSql = "UPDATE Vare SET Antal = Antal - " + purchasedQuantity + " WHERE VareId = " + this.vareId;
            int rowsAffected = statement.executeUpdate(updateStockSql);

            if (rowsAffected > 0) {
                success = true;
                this.antal -= purchasedQuantity;
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }
}