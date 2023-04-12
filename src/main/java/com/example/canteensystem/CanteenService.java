package com.example.canteensystem;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CanteenService {
    private List<Product> products;
    private List<Employee> employees;
    private List<Transaction> transactions;

    public CanteenService() {
        products = new ArrayList<>();
        employees = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean isValidEmployee(String employeeId) {
        int employeeIdInt;
        try {
            employeeIdInt = Integer.parseInt(employeeId);
        } catch (NumberFormatException e) {
            return false;
        }

        return employees.stream()
                .anyMatch(employee -> employee.getEmployeeId() == employeeIdInt);
    }

    public void updateStock(int productId, int newStock) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                product.setStock(newStock);
                break;
            }
        }
    }

    public boolean processTransaction(int transactionId, int employeeId, int productId, int quantity) {
        Employee employee = null;
        Product product = null;

        for (Employee e : employees) {
            if (e.getEmployeeId() == employeeId) {
                employee = e;
                break;
            }
        }

        for (Product p : products) {
            if (p.getProductId() == productId) {
                product = p;
                break;
            }
        }

        if (employee != null && product != null) {
            double totalPrice = product.getPrice() * quantity;
            if (employee.getBalance() >= totalPrice && product.getStock() >= quantity) {
                employee.setBalance(employee.getBalance() - totalPrice);
                product.setStock(product.getStock() - quantity);

                Transaction transaction = new Transaction(transactionId, employeeId, productId, quantity, LocalDateTime.now());
                transactions.add(transaction);
                return true;
            }
        }
        return false;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}

