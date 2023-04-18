package com.example.canteensystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CartEntry {
    private ObservableList<CartEntry> cartEntries;
    private int productId;
    private String productName;
    private double price;
    private int quantity;

    public CartEntry() {
        cartEntries = FXCollections.observableArrayList();
    }

    public CartEntry(int productId, String productName, double price) {
        this(productId, productName, price, 0);
    }

    public CartEntry(int productId, String productName, double price, int quantity) {
        this();
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    // Other methods and class definition



    public ObservableList<CartEntry> getCartEntries() {
        return cartEntries;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void clear() {
        cartEntries.clear();
    }

    @Override
    public String toString() {
        return productName + " - " + price + " kr x " + quantity;
    }

    public static class Cart {
        private ObservableList<CartEntry> cartEntries;

        public Cart() {
            cartEntries = FXCollections.observableArrayList();
        }

        public ObservableList<CartEntry> getCartEntries() {
            return cartEntries;
        }

        public void clear() {
            cartEntries.clear();
        }
    }
}
