package Login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the login + signup title screen.
 * Handles form switching, field validation, and sending
 * requests to LoginManager.
 */
public class TitleScreenController {

    private LoginManager loginManager = new LoginManager();

    // Forms
    @FXML private VBox loginForm;
    @FXML private VBox signupForm;

    // Toggle buttons
    @FXML private Button leftSignUpButton;
    @FXML private Button leftLoginButton;

    // Login inputs
    @FXML private TextField loginUsername;
    @FXML private PasswordField loginPassword;

    // Signup inputs
    @FXML private TextField signupUsername;
    @FXML private PasswordField signupPassword;
    @FXML private PasswordField confirmPassword;

    // GUI message labels
    @FXML private Label loginMessage;
    @FXML private Label signupMessage;

    @FXML
    public void initialize() {
        showLoginForm(); // default view
    }

    // LEFT BUTTON TOGGLE: LOGIN FORM
    @FXML
    private void showLoginForm() {
        loginForm.setVisible(true);
        loginForm.setManaged(true);

        signupForm.setVisible(false);
        signupForm.setManaged(false);

        loginMessage.setText("");
        signupMessage.setText("");

        leftLoginButton.getStyleClass().add("left-button-active");
        leftLoginButton.getStyleClass().remove("left-button-inactive");
        leftSignUpButton.getStyleClass().add("left-button-inactive");
    }

    // LEFT BUTTON TOGGLE: SIGNUP FORM
    @FXML
    private void showSignupForm() {
        signupForm.setVisible(true);
        signupForm.setManaged(true);

        loginForm.setVisible(false);
        loginForm.setManaged(false);

        loginMessage.setText("");
        signupMessage.setText("");

        leftSignUpButton.getStyleClass().add("left-button-active");
        leftSignUpButton.getStyleClass().remove("left-button-inactive");
        leftLoginButton.getStyleClass().add("left-button-inactive");
    }


    // LOAD CALENDAR SCREEN AFTER SUCCESSFUL LOGIN
    private void loadCalendarScreen() {
        try {
            // Load calendar.fxml from CalendarGUI package (must exist in resources)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CalendarGUI/calendar.fxml"));
            Parent root = loader.load();

            // Get current window (Stage)
            Stage stage = (Stage) loginForm.getScene().getWindow();

            // Replace scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            loginMessage.setText("Error loading calendar.");
        }
    }


    // LOGIN LOGIC (uses LoginManager + GUI message label)
    @FXML
    private void handleLogin() {
        loginMessage.setText("");

        String username = loginUsername.getText().trim();
        String password = loginPassword.getText().trim();

        // GUI checks
        if (username.isEmpty() || password.isEmpty()) {
            loginMessage.setText("Fill in all fields.");
            return;
        }

        // attempt login
        boolean success = loginManager.login(username, password);

        if (success) {
            loginMessage.setText("Login successful!");

            // LOAD CALENDAR SCREEN
            loadCalendarScreen();

        } else {
            loginMessage.setText("Invalid username or password.");
        }
    }

    // SIGNUP LOGIC (User.isValid() + LoginManager + GUI)
    @FXML
    private void handleSignup() {
        signupMessage.setText("");

        String username = signupUsername.getText().trim();
        String password = signupPassword.getText().trim();
        String confirm = confirmPassword.getText().trim();

        // GUI checks
        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            signupMessage.setText("Fill in all fields.");
            return;
        }

        if (!password.equals(confirm)) {
            signupMessage.setText("Passwords do not match.");
            return;
        }

        // validation using User.isValid()
        User user = new User(username, password);

        if (!user.isValid()) {
            signupMessage.setText("Username/password does not meet requirements.");
            return;
        }

        // send user to LoginManager
        boolean success = loginManager.register(user);

        if (success) {
            signupMessage.setText("Signup successful!");

            // automatically switch to login page
            showLoginForm();
            loginUsername.setText(username);

        } else {
            signupMessage.setText("Username already taken.");
        }
    }
}



