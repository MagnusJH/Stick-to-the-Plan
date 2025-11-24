import java.sql.*;

/**
 * UserDAO class handles all database operations for the "User" table.
 * It checks if a username exists, registers new users, and verifies logins.
 */
public class UserDAO {

    // Database connection URL
    private static final String URL = "jdbc:mysql://localhost:3306/sticktotheplan";

    // MySQL username for connecting to the database.
    private static final String USER = "root";

    // MySQL password for connecting to the database.
    private static final String PASSWORD = "password";

    /**
     * Checks if a given username already exists in the database.
     *
     * @param username the username to check
     * @return true if username exists, false otherwise
     */
    public boolean isUsernameTaken(String username) {

        // SQL query to count how many users have the given username
        String query = "SELECT COUNT(*) FROM user WHERE userName = ?";

        // Use try-with to automatically close connections
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); // Connect to database
                PreparedStatement stmt = conn.prepareStatement(query) // Create a prepared SQL statement
        ) {
            stmt.setString(1, username); // Replace '?' with the actual username
            ResultSet rs = stmt.executeQuery(); // Execute query and get results

            if (rs.next()) {
                // If count > 0, the username already exists
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            // Print error message if something goes wrong
            e.printStackTrace();
        }

        // If no result or error occurs, return false
        return false;
    }

    /**
     * Registers a new user if the username is not already taken.
     *
     * @param username desired username
     * @param password user's password
     * @return true if registration succeeds, false if
     *         the username is taken or if an error occurs
     */
    public int registerUser(String username, String password) {

        // First check if username already exists
        if (isUsernameTaken(username)) {
            return -1; // Exit method early
        }

        // SQL INSERT command to add a new user record
        String insert = "INSERT INTO user (userName, password) VALUES (?, ?)";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); // Open connection
                PreparedStatement stmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)
        ) {
            // Set each placeholder (?) with the correct value
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the INSERT command
            stmt.executeUpdate();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            // This happens if username is UNIQUE and already taken (extra safety)
            return false;
        } catch (SQLException e) {
            // Print full SQL error if connection or query fails
            e.printStackTrace();
        }

        // Return -1 if registration failed
        return false;
    }

    /**
     * Verifies login credentials by checking if username and password match.
     *
     * @param username the username entered by the user
     * @param password the password entered by the user
     * @return true if credentials are correct, false otherwise
     */
    public boolean verifyLogin(String username, String password) {

        // SQL query to find a record that matches both username and password
        String query = "SELECT * FROM user WHERE userName = ? AND password = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); // Connect to database
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            // Replace the placeholders with user inputs
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute query
            ResultSet rs = stmt.executeQuery();

            // If a record is found, login is successful
            return rs.next();

        } catch (SQLException e) {
            // Print detailed error if something goes wrong
            e.printStackTrace();
        }

        // Return false if login fails or error occurs
        return false;
    }
}
