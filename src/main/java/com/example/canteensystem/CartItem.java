package com.example.canteensystem;

public class CartItem {
    private Vare vare;
    private int quantity;

    public CartItem(Vare vare, int quantity) {
        this.vare = vare;
        this.quantity = quantity;
    }

    public Vare getVare() {
        return vare;
    }

    public void setVare(Vare vare) {
        this.vare = vare;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public double getTotalPrice() {
        return vare.getPris() * quantity;
    }
}
