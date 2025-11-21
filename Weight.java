import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class Weight extends FWC <WeightEntry>{
    Connector c = new Connector();
    Connection con = c.getConnection();
    private int weightID;

    public Weight(Connection con, int weightID) {
        super(con, weightID);
    }
    // INSERT INTO MYSQL TABLE
    protected void addEntry(WeightEntry entry) {
        String sql = "INSERT INTO weight (weightID, rowID, sets, reps, weight, wName) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1,parentID);
            stmt.setInt(2,entry.getRowID());
            stmt.setInt(3,entry.getSets());
            stmt.setInt(4,entry.getReps());
            stmt.setInt(5,entry.getWeight());
            stmt.setString(6,entry.getWName());

            stmt.executeUpdate();
            System.out.println("Food row inserted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
