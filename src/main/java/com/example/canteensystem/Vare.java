package com.example.canteensystem;

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

    // Getters and setters for each field

    public int getVareId() {
        return vareId;
    }

    public void setVareId(int vareId) {
        this.vareId = vareId;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public double getPris() {
        return pris;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }
}
