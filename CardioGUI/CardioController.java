package CardioGUI;

import Connector.Connector;
import MainFolder.SceneLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.sql.*;

public class CardioController {

    @FXML private TableView<CardioEntry> tableView;
    @FXML private TableColumn<CardioEntry, String> nameCol;
    @FXML private TableColumn<CardioEntry, Double> durationCol;
    @FXML private TableColumn<CardioEntry, String> durTypeCol;
    @FXML private TableColumn<CardioEntry, Double> distanceCol;
    @FXML private TableColumn<CardioEntry, String> distTypeCol;
    @FXML private TableColumn<CardioEntry, Integer> caloriesCol;

    @FXML private Button addRowButton;
    @FXML private Button deleteRowButton;
    @FXML private Button foodViewButton;
    @FXML private Button weightViewButton;
    @FXML private Button calendarViewButton;

    private final int cardioID;
    private final Connection con = new Connector().getConnection();
    private ObservableList<CardioEntry> data;

    public CardioController(int cardioID) {
        this.cardioID = cardioID;
    }

    @FXML
    public void initialize() {
        data = FXCollections.observableArrayList();
        tableView.setItems(data);
        tableView.setEditable(true);

        // Bind columns
        nameCol.setCellValueFactory(cell -> cell.getValue().cNameProperty());
        durationCol.setCellValueFactory(cell -> cell.getValue().durationProperty().asObject());
        durTypeCol.setCellValueFactory(cell -> cell.getValue().durTypeProperty());
        distanceCol.setCellValueFactory(cell -> cell.getValue().distanceProperty().asObject());
        distTypeCol.setCellValueFactory(cell -> cell.getValue().distTypeProperty());
        caloriesCol.setCellValueFactory(cell -> cell.getValue().caloriesBurnedProperty().asObject());

        // Make columns editable
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        durationCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        durTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        distanceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        distTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        caloriesCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // Commit edits → update model + DB
        nameCol.setOnEditCommit(e -> {
            e.getRowValue().setCName(e.getNewValue());
            updateRowInDB(e.getRowValue());
        });
        durationCol.setOnEditCommit(e -> {
            e.getRowValue().setDuration(e.getNewValue());
            updateRowInDB(e.getRowValue());
        });
        durTypeCol.setOnEditCommit(e -> {
            e.getRowValue().setDurType(e.getNewValue());
            updateRowInDB(e.getRowValue());
        });
        distanceCol.setOnEditCommit(e -> {
            e.getRowValue().setDistance(e.getNewValue());
            updateRowInDB(e.getRowValue());
        });
        distTypeCol.setOnEditCommit(e -> {
            e.getRowValue().setDistType(e.getNewValue());
            updateRowInDB(e.getRowValue());
        });
        caloriesCol.setOnEditCommit(e -> {
            e.getRowValue().setCaloriesBurned(e.getNewValue());
            updateRowInDB(e.getRowValue());
        });

        // Button actions
        addRowButton.setOnAction(e -> addRowToDB());
        deleteRowButton.setOnAction(e -> {
            CardioEntry selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                deleteRowFromDB(selected);
                data.remove(selected);
            }
        });

        foodViewButton.setOnAction(e -> {
            MainFolder.SceneLoader.getCardioStage().hide();
            MainFolder.SceneLoader.getFoodStage().show();
        });

        weightViewButton.setOnAction(e -> {
            MainFolder.SceneLoader.getCardioStage().hide();
            MainFolder.SceneLoader.getWeightStage().show();
        });

        calendarViewButton.setOnAction(e -> {
            MainFolder.SceneLoader.getCardioStage().close();
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

    // Load all cardio rows for this cardioID
    public void update() {
        data.clear();
        String sql = "SELECT * FROM cardio WHERE cardioID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cardioID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data.add(new CardioEntry(
                        rs.getInt("rowID"),
                        rs.getString("cName"),
                        rs.getDouble("duration"),
                        rs.getString("durType"),
                        rs.getDouble("distance"),
                        rs.getString("distType"),
                        rs.getInt("caloriesBurned")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert new row into DB
    private void addRowToDB() {
        try {
            String maxSQL = "SELECT MAX(rowID) FROM cardio WHERE cardioID = ?";
            PreparedStatement psMax = con.prepareStatement(maxSQL);
            psMax.setInt(1, cardioID);
            ResultSet rsMax = psMax.executeQuery();
            int nextRowID = 1;
            if (rsMax.next()) nextRowID = rsMax.getInt(1) + 1;

            String sql = "INSERT INTO cardio (cardioID, rowID, cName, duration, durType, distance, distType, caloriesBurned) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cardioID);
            ps.setInt(2, nextRowID);
            ps.setString(3, "New Exercise");
            ps.setDouble(4, 0.0);
            ps.setString(5, "m");
            ps.setDouble(6, 0.0);
            ps.setString(7, "mi");
            ps.setInt(8, 0);
            ps.executeUpdate();

            data.add(new CardioEntry(nextRowID, "New Exercise", 0.0, "m", 0.0, "mi", 0));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update row in DB
    private void updateRowInDB(CardioEntry row) {
        String sql = "UPDATE cardio SET cName=?, duration=?, durType=?, distance=?, distType=?, caloriesBurned=? WHERE cardioID=? AND rowID=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, row.getCName());
            ps.setDouble(2, row.getDuration());
            ps.setString(3, row.getDurType());
            ps.setDouble(4, row.getDistance());
            ps.setString(5, row.getDistType());
            ps.setInt(6, row.getCalBurned());
            ps.setInt(7, cardioID);
            ps.setInt(8, row.getRowID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update cardio entry in DB.").showAndWait();
        }
    }

    // Delete row from DB
    private void deleteRowFromDB(CardioEntry row) {
        String sql = "DELETE FROM cardio WHERE cardioID=? AND rowID=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cardioID);
            ps.setInt(2, row.getRowID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to delete cardio entry from DB.").showAndWait();
        }
    }
}

