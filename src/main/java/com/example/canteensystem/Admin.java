package com.example.canteensystem;

import java.sql.Connection;
import java.sql.Statement;

public class Admin {
    private int medarbejderNummer;
    private String fornavn;
    private String efternavn;
    private double pengePaaKonto;
    private String password;

    public Admin(int medarbejderNummer, String fornavn, String efternavn, double pengePaaKonto, String password) {
        this.medarbejderNummer = medarbejderNummer;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.pengePaaKonto = pengePaaKonto;
        this.password = password;
    }

    public void updateBalanceInDatabase() {
        try {
            Connection con = DB.DatabaseConnector.getConnection();
            Statement statement = con.createStatement();
            statement.executeUpdate("UPDATE Admin SET PengePaaKonto = " + getPengePaaKonto() + " WHERE MedarbejderNummer = " + getMedarbejderNummer());
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getters and setters for each field

    public int getMedarbejderNummer() {
        return medarbejderNummer;
    }

    public void setMedarbejderNummer(int medarbejderNummer) {
        this.medarbejderNummer = medarbejderNummer;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public void setEfternavn(String efternavn) {
        this.efternavn = efternavn;
    }

    public double getPengePaaKonto() {
        return pengePaaKonto;
    }

    public void setPengePaaKonto(double pengePaaKonto) {
        this.pengePaaKonto = pengePaaKonto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
