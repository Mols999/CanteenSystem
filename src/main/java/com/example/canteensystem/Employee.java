package com.example.canteensystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Employee getEmployeeById(int employeeId) {
        Employee employee = null;
        String query = "SELECT * FROM Medarbejder WHERE MedarbejderNummer = ?";

        try (Connection connection = DB.DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("Fornavn");
                    String lastName = resultSet.getString("Efternavn");
                    double balance = resultSet.getDouble("PengePaaKonto");
                    String password = resultSet.getString("Password");
                    boolean isAdmin = resultSet.getBoolean("isAdmin");
                    employee = new Employee(employeeId, firstName, lastName, balance, password, isAdmin);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting employee by ID: " + e.getMessage());
        }

        return employee;
    }
}
