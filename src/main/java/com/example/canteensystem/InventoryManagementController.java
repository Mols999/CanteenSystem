package com.example.canteensystem;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.DriverManager;
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
    private TableColumn<InventoryItem, Integer> quantityColumn;

    @FXML
    private Button reorderButton;

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFirmaId()));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("vareNavn"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

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
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-2NQ6KUQ8;databaseName=dbCanteen;user=sa;password=1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Leverandoer");

            while (resultSet.next()) {
                InventoryItem item = new InventoryItem(
                        resultSet.getString("FirmaId"),
                        resultSet.getString("Firma"),
                        resultSet.getString("VareId"),
                        resultSet.getString("VareNavn"),
                        resultSet.getInt("Antal")
                );
                items.add(item);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        inventoryTable.setItems(items);
    }

}