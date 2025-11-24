public class LoginManager {

    private UserDAO userDAO = new UserDAO();

    /**
     * Attempts to log in a user.
     *
     * @param username the entered username
     * @param password the entered password
     * @return true if login is successful
     */
    public boolean login(String username, String password) {
        return userDAO.verifyLogin(username, password);
    }

    /**
     * Attempts to register a user.
     *
     * @param username the desired username
     * @param password the desired password
     * @return true if registration succeeds
     */
    public boolean register(String username, String password) {
        return userDAO.registerUser(username, password);
    }
}