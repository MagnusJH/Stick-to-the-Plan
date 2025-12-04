package Login;

import java.sql.*;

/**
 * UserDAO class handles all database operations for the "user" table.
 * It checks if a username exists, registers new users, and verifies logins.
 */
public class UserDAO {

    // Database connection URL
    private static final String URL = "jdbc:mysql://localhost:3306/sticktotheplan";

    // MySQL login credentials
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    /**
     * Checks if a given username already exists in the database.
     *
     * @param username the username to check
     * @return true if username exists, false otherwise
     */
    public boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM user WHERE userName = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // username exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // default fallback
    }

    /**
     * Inserts a new user into the database if the username is not taken.
     *
     * @param username new username
     * @param password new password
     * @return true if registration succeeds
     */
    public boolean registerUser(String username, String password) {

        // Check username first
        if (isUsernameTaken(username)) {
            return false;
        }

        String insert = "INSERT INTO user (userName, password) VALUES (?, ?)";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(insert)
        ) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            stmt.executeUpdate();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            // Username is UNIQUE and already exists
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Verifies whether a username/password combination exists.
     *
     * @return true if login is valid
     */
    public boolean verifyLogin(String username, String password) {

        String query = "SELECT * FROM user WHERE userName = ? AND password = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            return rs.next(); // login successful if any row matches

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
