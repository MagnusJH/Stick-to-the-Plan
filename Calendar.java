import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.sql.Connection;

public class Calendar {

    // fields
    private int day;
    private int month;
    private int year;
    private int userID;
    private int dateID;

    // Constructor
    public Calendar(int userID) {
        this.userID = userID;
        Connection con = connect();
    }

    /**
     * This method will create a food, weight,
     * and cardio ID for this specific day
     * @param dateID
     */
    public void createDay(int dateID) {
        // sql.add(userID, dateID, day, month, year, newFoodID, newWeightID, newCardioID)
        createFoodPage(getFoodID(dateID));
        createWeightPage(getWeightID(dateID));
        createCardioPage(getCardioID(dateID));
    }

    /**
     * This method will create the FWCs for this day to be updated
     * @param dateID
     */
    public void updateDay(int dateID) {
        createFoodPage(getFoodID(dateID));
        createWeightPage(getWeightID(dateID));
        createCardioPage(getCardioID(dateID));
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
    private void createFoodPage(int foodID) {
        //FWC foodTable = new Food(foodID);
    }

    /**
     * Create the weight table
     * @param weightID
     */
    private void createWeightPage(int weightID) {
        // FWC weightTable = new Weight(weightID);
    }

    /**
     * Create the cardio table
     * @param cardioID
     */
    private void createCardioPage(int cardioID) {
        // FWC cardioTable = new Cardio(cardioID);
    }

    // GETTERS
    /**
     * // get the food id based on the day
     * @param dateID
     * @return foodID
     */
    public int getFoodID(int dateID) {
        // SELECT foodid
        // FROM calendar
        // WHERE userid = this.userID && dateid = this.dateID
        // ;

        // return foodID;
        return 0;
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

        // return weightID;
        return 0;
    }

    /**
     * // get the food id based on the day
     * @param dateID
     * @return cardioID
     */
    public int getCardioID(int dateID) {
        // SELECT cardioid
        // FROM calendar
        // WHERE userid = this.userID && dateid = this.dateID
        // ;

        // return cardioID;
        return 0;
    }

    public Connection connect() {
        // get the names of the database
        String url = "jdbc:mysql://localhost:3306/sticktotheplan";
        String username = "root";
        String password = "password";

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
