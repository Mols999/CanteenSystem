package com.example.canteensystem;

public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private double balance;
    private String password;

    private boolean isAdmin;

    public Employee(int employeeId, String firstName, String lastName, double balance, String password, boolean isAdmin) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.password = password;
        this.isAdmin = isAdmin;
    }

}
