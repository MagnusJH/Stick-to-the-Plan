package Login;

public class RegisterTest {
    public static void main(String[] args) {

        UserDAO dao = new UserDAO();

        testRegister(dao, "cruz", "password1");
        testRegister(dao, "john", "password2");
        testRegister(dao, "cruz", "password3");
    }

    private static void testRegister(UserDAO dao, String username, String password) {
        boolean success = dao.registerUser(username, password);

        if (success) {
            System.out.println("Registered Login.User: " + username);
        } else {
            System.out.println("Failed to register Login.User: " + username);
        }
    }
}
