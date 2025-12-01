package CardioGUI;

import Connector.Connector;
import Connector.FWC;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class Cardio extends FWC<CardioEntry> {
    Connector c = new Connector();
    Connection con = c.getConnection();
    private int cardioID;

    public Cardio(Connection con, int cardioID) {
        super(con, cardioID);
    }
    // INSERT INTO MYSQL TABLE
    protected void addEntry(CardioEntry entry) {
        String sql = "INSERT INTO cardio (cardioID, rowID, cName, duration, durType, distance, distType, caloriesBurned) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1,parentID);
            stmt.setInt(2,entry.getRowID());
            stmt.setString(3,entry.getCName());
            stmt.setDouble(4,entry.getDuration());
            stmt.setString(5,entry.getDurType());
            stmt.setDouble(6,entry.getDistance());
            stmt.setString(7, entry.getDistType());
            stmt.setInt(8, entry.getCalBurned());


            stmt.executeUpdate();
            System.out.println("Cardio.Cardio row inserted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
