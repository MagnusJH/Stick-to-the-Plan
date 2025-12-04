package Login;

/**
 * User class - represents a user object with a username,
 * password, and unique userID. It provides methods to add user,
 * and check if a user is valid.
 */
public class User {

    private String username;
    private String password;

    /**
     * Creates a new User object with a given username and password.
     *
     * @param username the username to assign
     * @param password the password to assign
     */
    public User (String username,String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Determines if both username and password pass validation rules.
     *
     * @return true if both username and password are valid
     */
    public boolean isValid() {
        return isUsernameValid() && isPasswordValid();
    }

    /**
     * Validate the username.
     * must not be null, must be at least 5 characters long, no spaces
     *
     * @return true if username meets all rules
     */
    private boolean isUsernameValid() {
        return username != null
                && username.length() >= 5
                && !username.contains(" ");
    }

    /**
     * Validates the password.
     * must not be null, must be at least 6 characters long, must contain 1 number
     * no spaces
     *
     * @return true if password meets all rules
     */
    private boolean isPasswordValid() {
        return password != null
                && password.length() >= 6
                && password.matches(".*\\d.*")   // must contain a digit
                && !password.contains(" ");
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
