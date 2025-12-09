package CalendarGUI;

import java.sql.*;

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
    public void createDay(String dateID) { // :)

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

    // GETTERS
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
        return 0;
    }

    public int getFWCID(String dateID) {
        int fwcID = 0;
        String query = "SELECT fwcID " +
                "FROM calendar " +
                "WHERE userID = this.userID && dateID = this.dateID";

        try (Statement stmt = con.createStatement()) {
            ResultSet result = stmt.executeQuery(query);
            result.next();
            fwcID = (result.getInt("fwvcID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // return foodID;
        return fwcID;
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
