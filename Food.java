import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class Food extends FWC <FoodEntry>{
    Connector c = new Connector();
    Connection con = c.getConnection();
    private int foodID;

    public Food(Connection con, int foodID) {
        super(con, foodID);
    }
    // INSERT INTO MYSQL TABLE
    protected void addEntry(FoodEntry entry) {
        String sql = "INSERT INTO food (foodID, rowID, foodName, cals, nutrNum, nutrName) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1,parentID);
            stmt.setInt(2,entry.getRowID());
            stmt.setString(3,entry.getFoodName());
            stmt.setInt(4,entry.getCals());
            stmt.setInt(4,entry.getNutrNum());
            stmt.setString(5,entry.getNutrName());

            stmt.executeUpdate();
            System.out.println("Food row inserted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
