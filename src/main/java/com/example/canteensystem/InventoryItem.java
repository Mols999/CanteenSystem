package com.example.canteensystem;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InventoryItem {
    private SimpleStringProperty firmaId;
    private SimpleStringProperty firma;
    private SimpleStringProperty vareId;
    private SimpleStringProperty vareNavn;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty prisPerEnhed;

    public InventoryItem(String firmaId, String firma, String vareId, String vareNavn, int quantity) {
        this.firmaId = new SimpleStringProperty(firmaId);
        this.firma = new SimpleStringProperty(firma);
        this.vareId = new SimpleStringProperty(vareId);
        this.vareNavn = new SimpleStringProperty(vareNavn);
        this.quantity = new SimpleIntegerProperty(quantity);
    }
    public String getFirmaId() {
        return firmaId.get();
    }

    public String getFirma() {
        return firma.get();
    }

    public String getVareId() {
        return vareId.get();
    }

    public String getVareNavn() {
        return vareNavn.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getPrisPerEnhed() {
        return prisPerEnhed.get();
    }
}