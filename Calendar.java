import java.sql.*;
import java.awt.event.ActionEvent;

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
        Connector c = new Connector();
        Connection con = c.getConnection();

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
     * @return foodID
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

}

