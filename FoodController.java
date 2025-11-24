package com.example.test;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import functionality.FoodEntry;

public class FoodController {

    @FXML private TableView<FoodEntry> tableView;

    @FXML private TableColumn<FoodEntry, String> foodCol;
    @FXML private TableColumn<FoodEntry, Integer> calCol;
    @FXML private TableColumn<FoodEntry, String> nutrientCol;
    @FXML private TableColumn<FoodEntry, Integer> numCol;

    @FXML private Button addRowButton;
    @FXML private Button deleteRowButton;

    private ObservableList<FoodEntry> data;

    @FXML
    public void initialize() {

        // Create list
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        tableView.setEditable(true);

        // --- Cell Factories (for editing)
        foodCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nutrientCol.setCellFactory(TextFieldTableCell.forTableColumn());

        calCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        numCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // --- Commit Handlers (save edits)
        foodCol.setOnEditCommit(event ->
                event.getRowValue().setFoodName(event.getNewValue()));

        calCol.setOnEditCommit(event ->
                event.getRowValue().setCals(event.getNewValue()));

        nutrientCol.setOnEditCommit(event ->
                event.getRowValue().setNutrName(event.getNewValue()));

        numCol.setOnEditCommit(event ->
                event.getRowValue().setNutrNum(event.getNewValue()));

        // --- Add Row Button
        addRowButton.setOnAction(e -> {
            data.add(new FoodEntry(0, "New Food", 0, 0, "Name"));
        });

        // --- Delete Row Button
        deleteRowButton.setOnAction(e -> {
            FoodEntry selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) data.remove(selected);
        });
    }
}
