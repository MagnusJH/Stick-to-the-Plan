package FoodGUI;

import Connector.Connector;
import MainFolder.SceneLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.sql.*;

public class FoodController {

    @FXML private TableView<FoodEntry> tableView;
    @FXML private TableColumn<FoodEntry, String> fName;
    @FXML private TableColumn<FoodEntry, Integer> cals;
    @FXML private TableColumn<FoodEntry, Integer> nutrNum;

    @FXML private Button addRowButton;
    @FXML private Button deleteRowButton;
    @FXML private Button weightViewButton;
    @FXML private Button cardioViewButton;
    @FXML private Button calendarViewButton;

    private ObservableList<FoodEntry> data;
    private final int foodID;

    private final Connection con = new Connector().getConnection();

    // Constructor
    public FoodController(int foodID) {
        this.foodID = foodID;
    }

    @FXML
    public void initialize() {
        data = FXCollections.observableArrayList();
        tableView.setItems(data);
        tableView.setEditable(true);

        // Bind Columns
        fName.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        cals.setCellValueFactory(cell -> cell.getValue().calsProperty().asObject());
        nutrNum.setCellValueFactory(cell -> cell.getValue().nutrNumProperty().asObject());

        // Editable Cells
        fName.setCellFactory(TextFieldTableCell.forTableColumn());
        cals.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        nutrNum.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // Commit Handlers → Update DB
        fName.setOnEditCommit(e -> {
            FoodEntry row = e.getRowValue();
            row.setFoodName(e.getNewValue());
            updateRowInDB(row);
        });

        cals.setOnEditCommit(e -> {
            FoodEntry row = e.getRowValue();
            row.setCals(e.getNewValue());
            updateRowInDB(row);
        });

        nutrNum.setOnEditCommit(e -> {
            FoodEntry row = e.getRowValue();
            row.setNutrNum(e.getNewValue());
            updateRowInDB(row);
        });


        // Add Row
        addRowButton.setOnAction(e -> addRowToDB());

        // Delete Row
        deleteRowButton.setOnAction(e -> {
            FoodEntry selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                deleteRowFromDB(selected);
                data.remove(selected);
            }
        });

        cardioViewButton.setOnAction(e -> {
            MainFolder.SceneLoader.getFoodStage().hide();
            MainFolder.SceneLoader.getCardioStage().show();
        });

        weightViewButton.setOnAction(e -> {
            MainFolder.SceneLoader.getFoodStage().hide();
            MainFolder.SceneLoader.getWeightStage().show();
        });

        calendarViewButton.setOnAction(e -> {
            MainFolder.SceneLoader.getFoodStage().close();
            SceneLoader loader = new SceneLoader();
            try {
                loader.calendarPage(SceneLoader.getUsername());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });



        // Load existing rows
        update();
    }

    // ---------------------------
    // Load all rows for this foodID
    // ---------------------------
    public void update() {
        data.clear();
        String sql = "SELECT * FROM food WHERE foodID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, foodID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                data.add(new FoodEntry(
                        rs.getInt("rowID"),
                        rs.getString("name"),
                        rs.getInt("calories"),
                        rs.getInt("nutrAmount")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------------------
    // INSERT new row into DB
    // ---------------------------
    private void addRowToDB() {
        try {
            // Get next rowID for this foodID
            String getMaxRowSQL = "SELECT MAX(rowID) FROM food WHERE foodID = ?";
            PreparedStatement psMax = con.prepareStatement(getMaxRowSQL);
            psMax.setInt(1, foodID);
            ResultSet rsMax = psMax.executeQuery();

            int nextRowID = 1;
            if (rsMax.next()) {
                nextRowID = rsMax.getInt(1) + 1; // increment
            }

            // Insert new row
            String sql = "INSERT INTO food (foodID, rowID, name, calories, nutrAmount) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, foodID);
            ps.setInt(2, nextRowID);
            ps.setString(3, "New Food Entry");
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.executeUpdate();

            // Add to table view
            data.add(new FoodEntry(nextRowID, "New Food Entry", 0, 0));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // ---------------------------
    // UPDATE existing row in DB
    // ---------------------------
    private void updateRowInDB(FoodEntry row) {
        String sql = "UPDATE food SET name=?, calories=?, nutrAmount=? WHERE rowID=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, row.getFoodName());
            ps.setInt(2, row.getCals());
            ps.setInt(3, row.getNutrNum());
            ps.setInt(4, row.getRowID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------------------
    // DELETE row from DB
    // ---------------------------
    private void deleteRowFromDB(FoodEntry row) {
        String sql = "DELETE FROM food WHERE rowID=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, row.getRowID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
