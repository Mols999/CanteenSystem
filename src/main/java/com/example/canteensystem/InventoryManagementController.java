package com.example.canteensystem;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class InventoryManagementController {

    private int MedarbejderNummer;
    private Main main;

    @FXML
    private TableView<InventoryItem> inventoryTable;

    @FXML
    private TableColumn<InventoryItem, String> idColumn;

    @FXML
    private TableColumn<InventoryItem, String> nameColumn;

    @FXML
    private TableColumn<InventoryItem, String> quantityColumn;

    @FXML
    private Button reorderButton;

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getItemId()));
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getItemName()));
        quantityColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getItemQuantity()));


        loadInventoryTable();
    }

    public void setMedarbejderNummer(int MedarbejderNummer) {
        this.MedarbejderNummer = MedarbejderNummer;
    }

    @FXML
    public void handleReorder() {
        // Add code to handle reorder button click
    }

    @FXML
    public void handleBack() {
        // Add code to handle back button click
    }

    public void loadInventoryTable() {
        ObservableList<InventoryItem> items = FXCollections.observableArrayList();

        try {
            Connection con = DB.DatabaseConnector.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Vare");

            while (resultSet.next()) {
                InventoryItem item = new InventoryItem(
                        resultSet.getString("vareId"),
                        resultSet.getString("Navn"),
                        resultSet.getString("Antal")

                );
                items.add(item);
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        inventoryTable.setItems(items);
    }

}