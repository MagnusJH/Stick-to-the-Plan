package FoodGUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FoodController {

    @FXML private TableView<FoodGUI.FoodEntry> tableView;
    @FXML private TableColumn<FoodGUI.FoodEntry, String> fName;
    @FXML private TableColumn<FoodGUI.FoodEntry, Integer> cals;
    @FXML private TableColumn<FoodGUI.FoodEntry, Integer> nutrNum;


    @FXML private Button addRowButton;
    @FXML private Button deleteRowButton;
    @FXML private Button weightViewButton;
    @FXML private Button cardioViewButton;

    private ObservableList<FoodEntry> data;
    MainFolder.SceneLoader loader = new MainFolder.SceneLoader();

    @FXML
    public void initialize() {

        // Create list
        data = FXCollections.observableArrayList();
        tableView.setItems(data);

        tableView.setEditable(true);

        // Bind values
        fName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        cals.setCellValueFactory(data -> data.getValue().calsProperty().asObject());
        nutrNum.setCellValueFactory(data -> data.getValue().nutrNumProperty().asObject());
        // --- Cell Factories (for editing)
        fName.setCellFactory(TextFieldTableCell.forTableColumn());
        cals.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        nutrNum.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // --- Commit Handlers (save edits)
        fName.setOnEditCommit(event ->
                event.getRowValue().setFoodName(event.getNewValue()));

        cals.setOnEditCommit(event ->
                event.getRowValue().setCals(event.getNewValue()));

        nutrNum.setOnEditCommit(event ->
                event.getRowValue().setNutrNum(event.getNewValue()));

        // --- Add Row Button
        addRowButton.setOnAction(e -> {
            data.add(new FoodEntry(0, "New Food Entry", 0, 0));
        });

        // --- Delete Row Button
        deleteRowButton.setOnAction(e -> {
            FoodGUI.FoodEntry selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) data.remove(selected);
        });

        weightViewButton.setOnAction(e -> {
            try {
                loader.weightPage(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Weight Page could not be loaded");
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
