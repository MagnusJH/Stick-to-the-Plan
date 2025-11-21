import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.sql.Connection;

public class Calendar {

    // fields
    private int userID;
    private String dateID;
    Connection con;

    // Constructor
    public Calendar(int userID) {
        this.userID = userID;
        this.con = connect();
    }

    /**
     * This method will create a food, weight,
     * and cardio ID for this specific day
     * @param dateID
     */
    public void createDay(String dateID) {

        // try block to run sql statement
        try {
            // create the statement
            String createDay = "INSERT INTO calendar (userid, dateid) VALUES " +
                    "(?, ?)";
            PreparedStatement prepState = con.prepareStatement(createDay);

            // put the values into the statement
            prepState.setInt(1, userID);
            prepState.setString(2, dateID);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid create");
        }
    }

    public void printDay(String dateID) {
        // try block to run sql statement
        try {
            // create the statement
            String selectDay = "SELECT * FROM calendar WHERE userid = ? AND dateid = ?";
            PreparedStatement prepState = con.prepareStatement(selectDay);

            // put the values into the statement
            prepState.setInt(1, userID);
            prepState.setString(2, dateID);

        } catch (Exception e) {
            System.out.println("Invalid day");
        }
    }

    /**
     * This method will check with the database
     * to see if this day already exists
     * @returns a true or false value depending on if the day exists
     */
    public boolean dayExists(int dateID) {
        /*
        // day exists
        if (SELECT userid, dateid != null) {
            return true;
        // day does not exist
        } else {
            return false;
         */

        return true;
    }

    /**
     * Create the food page
     * @param foodID
     */
    private void createFoodPage(Connection con, int foodID) throws Exception {
        FWC<FoodEntry> foodTable = new Food(con,foodID);
        foodTable.addRow(new FoodEntry(1,"corn",260,6,"protein"));
    }

    /**
     * Create the weight table
     * @param weightID
     */
    private void createWeightPage(Connection con, int weightID) {
        // FWC weightTable = new Weight(weightID);
        FWC<WeightEntry> weightTable = new Weight(con, weightID);
        weightTable.addRow(new WeightEntry(1,3000000,100000000,50,"Bicep Curls"));

    }

    /**
     * Create the cardio table
     * @param cardioID
     */
    private void createCardioPage(Connection con, int cardioID) {
        // FWC cardioTable = new Cardio(cardioID);
        FWC<CardioEntry> cardioTable = new Cardio(con, cardioID);
        cardioTable.addRow(new CardioEntry(1,1,"run",100,"min",11.5,"miles",10));

    }

    // GETTERS
    /**
     * // get the food id based on the day
     * @param dateID
     * @return fwcID
     */
    public int getFoodID(Connection con, int dateID) {
        int foodID = 0;
        String query = "SELECT foodID " +
                "FROM calendar " +
                "WHERE userID = this.userID && dateID = this.dateID";

        try (Statement stmt = con.createStatement()) {
            ResultSet result = stmt.executeQuery(query);
            result.next();
            foodID = (result.getInt("foodID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // return foodID;
        return foodID;
    }

    /**
     * // get the wieght id based on the day
     * @param dateID
     * @return weightID
     */
    public int getWeightID(int dateID) {
        // SELECT weightid
        // FROM calendar
        // WHERE userid = this.userID && dateid = this.dateID
        // ;
    public int getFWCID(String dateID) {
        // try block to run sql statement
        try {
            // create the statement
            String selectFood = "SELECT fwcid FROM calendar WHERE dateID = ? AND userID = ?";
            PreparedStatement prepState = con.prepareStatement(selectFood);

            // put the values into the statement
            prepState.setString(1, dateID);
            prepState.setInt(2, userID);

        } catch (Exception e) {
            System.out.println("Invalid food");
        }

        // return foodID;
        return 0;
    }

    public Connection connect() {
        // get the names of the database
        String url = "jdbc:mysql://localhost:3306/sticktotheplan";
        String username = "root";
        String password = "5UOemu5d#";

        // try to open connection
        Connection con = null;
        try {
            // connection
            con = DriverManager.getConnection(url, username, password);
            System.out.println("connected");
            return con;

        } catch (Exception e) {
            // error connecting
            System.out.println("exception: " + e.getMessage());
            System.exit(1);
        }

        return con;
    }
}
