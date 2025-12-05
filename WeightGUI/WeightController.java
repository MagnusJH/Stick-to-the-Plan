package WeightGUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class WeightController implements Initializable {

    @FXML private TableView<WeightEntry> table;

    @FXML private TableColumn<WeightEntry, String> exercise;
    @FXML private TableColumn<WeightEntry, Integer> set;
    @FXML private TableColumn<WeightEntry, Integer> rep;
    @FXML private TableColumn<WeightEntry, Integer> weight;

    @FXML private Button addRowButton;
    @FXML private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        table.setEditable(true);

        // Bind columns to WeightEntry properties
        exercise.setCellValueFactory(new PropertyValueFactory<>("wName"));
        set.setCellValueFactory(cellData -> cellData.getValue().setsProperty().asObject());
        rep.setCellValueFactory(cellData -> cellData.getValue().repsProperty().asObject());
        weight.setCellValueFactory(cellData -> cellData.getValue().weightProperty().asObject());

        // Editable columns
        exercise.setCellFactory(TextFieldTableCell.forTableColumn());
        exercise.setOnEditCommit(e ->
                e.getRowValue().setWName(e.getNewValue())
        );

        set.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        set.setOnEditCommit(e ->
                e.getRowValue().setSets(e.getNewValue())
        );

        rep.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        rep.setOnEditCommit(e ->
                e.getRowValue().setReps(e.getNewValue())
        );

        weight.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        weight.setOnEditCommit(e ->
                e.getRowValue().setWeight(e.getNewValue())
        );

        // Add row button
        addRowButton.setOnAction(event -> {
            table.getItems().add(new WeightEntry(1,"Exercise", 1, 1, 0));
        });

        // Delete selected row button
        deleteButton.setOnAction(event -> {
            WeightEntry selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                table.getItems().remove(selected);
            }
        });
    }
}
