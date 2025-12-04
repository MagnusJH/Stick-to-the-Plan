package Login;

/**
 * Handles authentication and registration requests.
 * Acts as a bridge between the GUI controller and the UserDAO.
 */
public class LoginManager {

    private UserDAO userDAO = new UserDAO();  // Handles all DB operations

    /**
     * Attempts to log in a user.
     *
     * @param username entered username
     * @param password entered password
     * @return true if login is successful
     */
    public boolean login(String username, String password) {
        return userDAO.verifyLogin(username, password);
    }

    /**
     * Attempts to register a user using a User object.
     * User.isValid() should be checked before this method is called.
     *
     * @param user validated User object
     * @return true if registration succeeds
     */
    public boolean register(User user) {
        return userDAO.registerUser(user.getUsername(), user.getPassword());
    }
}

