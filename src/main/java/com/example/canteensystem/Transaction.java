package com.example.canteensystem;

import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;
    private int employeeId;
    private int productId;
    private int quantity;
    private LocalDateTime timestamp;

    public Transaction(int transactionId, int employeeId, int productId, int quantity, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.employeeId = employeeId;
        this.productId = productId;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }


    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", employeeId=" + employeeId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", timestamp=" + timestamp +
                '}';
    }
}

