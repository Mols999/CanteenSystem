package com.example.canteensystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRepository {

    public Employee getEmployeeById(int employeeId) {
        Employee employee = null;
        String query = "SELECT * FROM Medarbejder WHERE MedarbejderNummer = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("Fornavn");
                    String lastName = resultSet.getString("Efternavn");
                    double balance = resultSet.getDouble("PengePaaKonto");
                    String password = resultSet.getString("Password");
                    employee = new Employee(employeeId, firstName, lastName, balance, password);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting employee by ID: " + e.getMessage());
        }

        return employee;
    }
}
