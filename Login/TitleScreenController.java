package TitleScreen;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TitleScreenController {

    private LoginManager loginManager = new LoginManager();

    @FXML private VBox loginForm;
    @FXML private VBox signupForm;

    @FXML private Button leftSignUpButton;
    @FXML private Button leftLoginButton;

    @FXML private TextField loginUsername;
    @FXML private PasswordField loginPassword;
    @FXML private Label loginMessage;
    @FXML private Label loginSuccess;

    @FXML private TextField signupUsername;
    @FXML private PasswordField signupPassword;
    @FXML private PasswordField confirmPassword;
    @FXML private Label signupMessage;
    @FXML private Label signupSuccess;

    @FXML
    public void initialize() {
        showLoginForm();
    }

    // TOGGLE PAGES
    @FXML
    private void showLoginForm() {
        loginForm.setVisible(true);
        loginForm.setManaged(true);
        signupForm.setVisible(false);
        signupForm.setManaged(false);

        // clear messages when switching
        loginMessage.setText("");
        signupMessage.setText("");

        leftLoginButton.getStyleClass().add("left-button-active");
        leftLoginButton.getStyleClass().remove("left-button-inactive");
        leftSignUpButton.getStyleClass().add("left-button-inactive");
    }

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


    // LOGIN
    @FXML
    private void handleLogin() {
        loginMessage.setText(""); // clear previous message

        String username = loginUsername.getText().trim();
        String password = loginPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            loginMessage.setText("Please fill in all fields.");
            return;
        }

        boolean success = loginManager.login(username, password);

        if (success) {
            loginSuccess.setText("Login successful!");

            // calendar.fxml

        } else {
            loginMessage.setText("Invalid username or password.");
        }
    }


    // SIGN UP
    @FXML
    private void handleSignup() {
        signupMessage.setText(""); // clear previous message

        String username = signupUsername.getText().trim();
        String password = signupPassword.getText().trim();
        String confirm = confirmPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            signupMessage.setText("Please fill in all fields.");
            return;
        }

        if (!password.equals(confirm)) {
            signupMessage.setText("Passwords do not match.");
            return;
        }

        boolean success = loginManager.register(username, password);

        if (success) {
            signupSuccess.setText("Signup successful!");

            showLoginForm();
            loginUsername.setText(username);
        } else {
            signupMessage.setText("Username already taken.");
        }
    }
}



