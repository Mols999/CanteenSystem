package com.example.canteensystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    private static Connection con;
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static String port;
    private static String databaseName;
    private static String userName;
    private static String password;

    public static final String NOMOREDATA = "|ND|";
    private static int numberOfColumns;
    private static int currentColumnNumber = 1;

    private static boolean moreData = false;
    private static boolean pendingData = false;
    private static boolean terminated = false;

    private DB() {
    }

    static {
        Properties props = new Properties();
        String fileName = "db.properties";
        InputStream input;
        try {
            input = new FileInputStream(fileName);
            props.load(input);
            port = props.getProperty("port", "1433");
            databaseName = props.getProperty("databaseName");
            userName = props.getProperty("userName", "sa");
            password = props.getProperty("password","1234");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Database Ready");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public class DatabaseConnector {

        private static final String CONNECTION_URL = "jdbc:sqlserver://LAPTOP-2NQ6KUQ8;databaseName=dbCanteen;user=sa;password=1234";

        public static Connection getConnection() {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(CONNECTION_URL);
            } catch (SQLException e) {
                System.out.println("Error connecting to the database: " + e.getMessage());
            }
            return connection;
        }
    }

    private static void connect() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-2NQ6KUQ8:"+port+";databaseName="+databaseName, userName, password);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void disconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void selectSQL(String sql) {
        if (terminated){
            System.exit(0);
        }
        try {
            if (ps!=null){
                ps.close();
            }
            if (rs!=null){
                rs.close();
            }
            connect();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            pendingData=true;
            moreData = rs.next();
            ResultSetMetaData rsmd = rs.getMetaData();
            numberOfColumns = rsmd.getColumnCount();
        } catch (Exception e){
            System.err.println("Error in the sql parameter, please test this in SQLServer first");
            System.err.println(e.getMessage());
        }
    }

    public static String getDisplayData() {
        if (terminated){
            System.exit(0);
        }
        if (!pendingData){
            terminated=true;
            throw new RuntimeException("ERROR! No previous select, communication with the database is lost!");
        } else if (!moreData){
            disconnect();
            pendingData=false;
            return NOMOREDATA;
        } else {
            return getNextValue(true);
        }
    }

    public static int getNumberOfColumns() {
        return numberOfColumns;
    }

    public static String getData() {
        if (terminated){
            System.exit(0);
        }
        if (!pendingData){
            terminated=true;
            throw new RuntimeException("ERROR! No previous select, communication with the database is lost!");
        } else if (!moreData){
            disconnect();
            pendingData=false;
            return NOMOREDATA;
        } else {
            return getNextValue(false).trim();
        }
    }

    private static String getNextValue(boolean view) {
        StringBuilder value= new StringBuilder();
        try {
            value.append(rs.getString(currentColumnNumber));
            if (currentColumnNumber >= numberOfColumns){
                currentColumnNumber = 1;
                if (view) { value.append("\n");
                }
                moreData = rs.next();
            } else {
                if (view) {
                    value.append(" ");
                }
                currentColumnNumber++;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return value.toString();
    }

    public static boolean insertSQL(String sql) {
        return executeUpdate(sql);
    }

    public static boolean updateSQL(String sql) {
        return executeUpdate(sql);
    }

    public static boolean deleteSQL(String sql) {
        return executeUpdate(sql);
    }

    private static boolean executeUpdate(String sql) {
        if (terminated) {
            System.exit(0);
        }
        if (pendingData) {
            terminated = true;
            throw new RuntimeException("ERROR! There were pending data from previous select, communication with the database is lost!");
        }
        try {
            if (ps != null) {
                ps.close();
            }
            connect();
            ps = con.prepareStatement(sql);
            int rows = ps.executeUpdate();
            ps.close();
            if (rows > 0) {
                return true;
            }
        } catch (RuntimeException | SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }
        return false;
    }


    public static boolean createEmployee(int employeeId, String name, double balance, String password, boolean isAdmin) {
        boolean success = false;
        try {
            connect();
            String sql = "INSERT INTO employees (employee_id, name, balance) VALUES (?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, employeeId);
            ps.setString(2, name);
            ps.setDouble(3, balance);
            int rowsAffected = ps.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error in createEmployee method in DB class: " + e.getMessage());
        } finally {
            disconnect();
        }
        return success;
    }

    public static Employee login(int employeeId, String password) {
        Employee employee = null;
        try {
            connect();
            String sql = "SELECT MedarbejderNummer, Fornavn, Efternavn, PengePaaKonto, Password, CASE WHEN Admin.MedarbejderNummer IS NULL THEN 0 ELSE 1 END as IsAdmin FROM Medarbejder LEFT JOIN Admin ON Medarbejder.MedarbejderNummer = Admin.MedarbejderNummer WHERE Medarbejder.MedarbejderNummer = ? AND Password = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, employeeId);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("Fornavn");
                String lastName = rs.getString("Efternavn");
                double balance = rs.getDouble("PengePaaKonto");
                boolean isAdmin = rs.getBoolean("IsAdmin");
                employee = new Employee(employeeId, firstName, lastName, balance, password, isAdmin);
            }
        } catch (SQLException e) {
            System.err.println("Error in login method in DB class: " + e.getMessage());
        } finally {
            disconnect();
        }
        return employee;
    }

    public static boolean createProduct(int productId, String name, double price, int stock) {
        boolean success = false;
        try {
            connect();
            String sql = "INSERT INTO products (product_id, name, price, stock) VALUES (?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setInt(4, stock);
            int rowsAffected = ps.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error in createProduct method in DB class: " + e.getMessage());
        } finally {
            disconnect();
        }
        return success;
    }

    public static boolean createTransaction(int transactionId, int employeeId, int productId, int quantity, String timestamp) {
        boolean success = false;
        try {
            connect();
            String sql = "INSERT INTO transactions (transaction_id, employee_id, product_id, quantity, timestamp) VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, transactionId);
            ps.setInt(2, employeeId);
            ps.setInt(3, productId);
            ps.setInt(4, quantity);
            ps.setString(5, timestamp);
            int rowsAffected = ps.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error in createTransaction method in DB class: " + e.getMessage());
        } finally {
            disconnect();
        }
        return success;
    }

    public static boolean updateProductStock(int productId, int stock) {
        boolean success = false;
        try {
            connect();
            String sql = "UPDATE products SET stock = ? WHERE product_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, stock);
            ps.setInt(2, productId);
            int rowsAffected = ps.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error in updateProductStock method in DB class: " + e.getMessage());
        } finally {
            disconnect();
        }
        return success;
    }

    public static boolean updateEmployeeBalance(int employeeId, double balance) {
        boolean success = false;
        try {
            connect();
            String sql = "UPDATE employees SET balance = ? WHERE employee_id = ?";
            ps = con.prepareStatement(sql);
            ps.setDouble(1, balance);
            ps.setInt(2, employeeId);
            int rowsAffected = ps.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error in updateEmployeeBalance method in DB class: " + e.getMessage());
        } finally {
            disconnect();
        }
        return success;
    }

}
