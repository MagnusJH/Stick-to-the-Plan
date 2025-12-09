package WeightGUI;

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

public class WeightController {

    @FXML private TableView<WeightEntry> tableView;
    @FXML private TableColumn<WeightEntry, String> wName;
    @FXML private TableColumn<WeightEntry, Integer> sets;
    @FXML private TableColumn<WeightEntry, Integer> reps;
    @FXML private TableColumn<WeightEntry, Integer> weight;

    @FXML private Button addRowButton;
    @FXML private Button deleteRowButton;
    @FXML private Button foodViewButton;
    @FXML private Button cardioViewButton;
    @FXML private Button calendarViewButton;

    private ObservableList<WeightEntry> data;
    private final int weightID;

    private final Connection con = new Connector().getConnection();

    public WeightController(int weightID) {
        this.weightID = weightID;
    }

    @FXML
    public void initialize() {
        data = FXCollections.observableArrayList();
        tableView.setItems(data);
        tableView.setEditable(true);

        // Bind columns to properties
        wName.setCellValueFactory(new PropertyValueFactory<>("wName")); // matches WeightEntry property
        sets.setCellValueFactory(cell -> cell.getValue().setsProperty().asObject());
        reps.setCellValueFactory(cell -> cell.getValue().repsProperty().asObject());
        weight.setCellValueFactory(cell -> cell.getValue().weightProperty().asObject());

        // Editable cells
        wName.setCellFactory(TextFieldTableCell.forTableColumn());
        sets.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        reps.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        weight.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // Commit edits → update DB immediately
        wName.setOnEditCommit(e -> {
            e.getRowValue().setWName(e.getNewValue());
            updateRowInDB(e.getRowValue());
        });
        sets.setOnEditCommit(e -> {
            e.getRowValue().setSets(e.getNewValue());
            updateRowInDB(e.getRowValue());
        });
        reps.setOnEditCommit(e -> {
            e.getRowValue().setReps(e.getNewValue());
            updateRowInDB(e.getRowValue());
        });
        weight.setOnEditCommit(e -> {
            e.getRowValue().setWeight(e.getNewValue());
            updateRowInDB(e.getRowValue());
        });

        // Add new row
        addRowButton.setOnAction(e -> addRowToDB());

        // Delete selected row
        deleteRowButton.setOnAction(e -> {
            WeightEntry selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                deleteRowFromDB(selected);
                data.remove(selected);
            }
        });

        foodViewButton.setOnAction(e -> {
            MainFolder.SceneLoader.getWeightStage().hide();
            MainFolder.SceneLoader.getFoodStage().show();
        });

        cardioViewButton.setOnAction(e -> {
            MainFolder.SceneLoader.getWeightStage().hide();
            MainFolder.SceneLoader.getCardioStage().show();
        });

        calendarViewButton.setOnAction(e -> {
            MainFolder.SceneLoader.getWeightStage().close();
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

    // Load all rows for this weightID
    public void update() {
        data.clear();
        String sql = "SELECT * FROM weights WHERE weightID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, weightID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data.add(new WeightEntry(
                        rs.getInt("rowID"),
                        rs.getString("wName"),
                        rs.getInt("sets"),
                        rs.getInt("reps"),
                        rs.getInt("weight")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert new row into DB
    private void addRowToDB() {
        try {
            // Get next rowID
            String getMaxSQL = "SELECT MAX(rowID) FROM weights WHERE weightID = ?";
            PreparedStatement psMax = con.prepareStatement(getMaxSQL);
            psMax.setInt(1, weightID);
            ResultSet rsMax = psMax.executeQuery();
            int nextRowID = 1;
            if (rsMax.next()) nextRowID = rsMax.getInt(1) + 1;

            // Insert
            String sql = "INSERT INTO weights (weightID, rowID, sets, reps, weight, wName) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, weightID);
            ps.setInt(2, nextRowID);
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.setString(6, "New Exercise");
            ps.executeUpdate();

            data.add(new WeightEntry(nextRowID, "New Exercise", 0, 0, 0));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update existing row in DB
    private void updateRowInDB(WeightEntry row) {
        String sql = "UPDATE weights SET sets=?, reps=?, weight=?, wName=? WHERE weightID=? AND rowID=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, row.getSets());
            ps.setInt(2, row.getReps());
            ps.setInt(3, row.getWeight());
            ps.setString(4, row.getWName());
            ps.setInt(5, weightID);
            ps.setInt(6, row.getRowID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete row from DB
    private void deleteRowFromDB(WeightEntry row) {
        String sql = "DELETE FROM weights WHERE weightID=? AND rowID=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, weightID);
            ps.setInt(2, row.getRowID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
