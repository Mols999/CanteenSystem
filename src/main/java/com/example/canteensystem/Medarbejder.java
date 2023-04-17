package com.example.canteensystem;
public class Medarbejder {
    private int medarbejderNummer;
    private String fornavn;
    private String efternavn;
    private double pengePaaKonto;
    private String password;

    public Medarbejder(int medarbejderNummer, String fornavn, String efternavn, double pengePaaKonto, String password) {
        this.medarbejderNummer = medarbejderNummer;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.pengePaaKonto = pengePaaKonto;
        this.password = password;
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

