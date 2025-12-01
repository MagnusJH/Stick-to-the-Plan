/**
 * User class - represents a user object with a username,
 * password, and unique userID. It provides methods to add user,
 * and check if a user is valid.
 */
public class User {

    // fields
    private String username;
    private String password;

    /**
     * Creates a new User object with a given username and password.
     *
     * @param username
     * @param password
     */
    public User (int userID, String username,String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    /**
     * Checks if user's username and password are valid.
     * A valid user has a non-empty username and password.
     *
     * @return true if both username and password are not
     * empty, false otherwise
     */
    public boolean isValid() {
        // will validate if username is taken with MySQL.
        return username != null && !username.isEmpty()
                && password != null && !password.isEmpty();
    }

    /**
     * sets the user's password
     *
     * @param password users password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * sets username
     *
     * @param username users username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * returns the user's password
     *
     * @return password
     */
    public String getPassword () {
        return password;
    }

    /**
     * returns the user's username
     *
     * @return username
     */
    public String getUsername () {
        return username;
    }
}
