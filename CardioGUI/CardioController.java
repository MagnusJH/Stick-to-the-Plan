
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class CardioController implements Initializable {

    @FXML private TableView<CardioEntry> cardioTable;

    @FXML private TableColumn<CardioEntry, String> nameCol;
    @FXML private TableColumn<CardioEntry, Double> durationCol;
    @FXML private TableColumn<CardioEntry, String> durTypeCol;
    @FXML private TableColumn<CardioEntry, Double> distanceCol;
    @FXML private TableColumn<CardioEntry, String> distTypeCol;
    @FXML private TableColumn<CardioEntry, Integer> caloriesCol;

    @FXML private Button addCardioRow;
    @FXML private Button deleteCardioRow;

    private final ObservableList<CardioEntry> entries = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cardioTable.setEditable(true);
        cardioTable.setItems(entries);

        // Bind values
        nameCol.setCellValueFactory(data -> data.getValue().cNameProperty());
        durationCol.setCellValueFactory(data -> data.getValue().durationProperty().asObject());
        durTypeCol.setCellValueFactory(data -> data.getValue().durTypeProperty());
        distanceCol.setCellValueFactory(data -> data.getValue().distanceProperty().asObject());
        distTypeCol.setCellValueFactory(data -> data.getValue().distTypeProperty());
        caloriesCol.setCellValueFactory(data -> data.getValue().caloriesBurnedProperty().asObject());

        // Make columns editable

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(e -> e.getRowValue().setCName(e.getNewValue()));

        durTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        durTypeCol.setOnEditCommit(e -> e.getRowValue().setDurType(e.getNewValue()));

        distTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        distTypeCol.setOnEditCommit(e -> e.getRowValue().setDistType(e.getNewValue()));

        durationCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        durationCol.setOnEditCommit(e -> e.getRowValue().setDuration(e.getNewValue()));

        distanceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        distanceCol.setOnEditCommit(e -> e.getRowValue().setDistance(e.getNewValue()));

        caloriesCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        caloriesCol.setOnEditCommit(e -> e.getRowValue().setCaloriesBurned(e.getNewValue()));

        // Button actions
        addCardioRow.setOnAction(e -> addRow());
        deleteCardioRow.setOnAction(e -> deleteRow());
    }

    private void addRow() {
        int id = entries.size() + 1;
        entries.add(new CardioEntry(
                id, id,
                "New Exercise",
                0.0, "min",
                0.0, "mi",
                0
        ));
    }

    private void deleteRow() {
        CardioEntry selected = cardioTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            entries.remove(selected);
        }
    }
}
