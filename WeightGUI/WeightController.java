package WeightGUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WeightController {

    @FXML private TableView<WeightGUI.WeightEntry> tableView;
    @FXML private TableColumn<WeightGUI.WeightEntry, String> wName;
    @FXML private TableColumn<WeightGUI.WeightEntry, Integer> sets;
    @FXML private TableColumn<WeightGUI.WeightEntry, Integer> reps;
    @FXML private TableColumn<WeightGUI.WeightEntry, Integer> weight;


    @FXML private Button addRowButton;
    @FXML private Button deleteRowButton;
    @FXML private Button foodViewButton;
    @FXML private Button cardioViewButton;

    private ObservableList<WeightEntry> data;
    MainFolder.SceneLoader loader = new MainFolder.SceneLoader();

    @FXML
    public void initialize() {

        // Create list
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        tableView.setEditable(true);

        // Bind values
        wName.setCellValueFactory(new PropertyValueFactory<>("wName"));
        sets.setCellValueFactory(data -> data.getValue().setsProperty().asObject());
        reps.setCellValueFactory(data -> data.getValue().repsProperty().asObject());
        weight.setCellValueFactory(data -> data.getValue().weightProperty().asObject());

        // --- Cell Factories (for editing)
        wName.setCellFactory(TextFieldTableCell.forTableColumn());
        sets.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        reps.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        weight.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // --- Commit Handlers (save edits)
        wName.setOnEditCommit(event ->
                event.getRowValue().setWName(event.getNewValue()));

        sets.setOnEditCommit(event ->
                event.getRowValue().setSets(event.getNewValue()));

        reps.setOnEditCommit(event ->
                event.getRowValue().setReps(event.getNewValue()));

        weight.setOnEditCommit(event ->
                event.getRowValue().setWeight(event.getNewValue()));

        // --- Add Row Button
        addRowButton.setOnAction(e -> {
            data.add(new WeightEntry(0, "New Exercise", 0, 0, 0));
        });

        // --- Delete Row Button
        deleteRowButton.setOnAction(e -> {
            WeightGUI.WeightEntry selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) data.remove(selected);
        });

        foodViewButton.setOnAction(e -> {
            try {
                loader.foodPage(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Food Page could not be loaded");
            }
        });

        cardioViewButton.setOnAction(e -> {
            try {
                loader.cardioPage(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Cardio Page could not be loaded");
            }
        });
    }
}
