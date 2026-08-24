package org.example.academic.system.view.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.academic.system.controller.AuthenticationController;
import org.example.academic.system.exception.AuthenticationException;
import org.example.academic.system.model.User;
import org.example.academic.system.repository.UserTxtRepository;
import org.example.academic.system.security.AuthenticationService;

/**
 * TUS-2407 - Create JavaFX login screen.
 *
 * Handles authentication via the graphical interface.
 * Delegates authentication to AuthenticationController (TUS-2414).
 * Passwords are never displayed as plain text.
 */
public class LoginScreenController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;

    private final AuthenticationController authController;

    public LoginScreenController() {
        AuthenticationService authService = new AuthenticationService(new UserTxtRepository());
        this.authController = new AuthenticationController(authService);
    }

    @FXML
    public void initialize() {
        errorLabel.setVisible(false);
        loginButton.setOnAction(e -> handleLogin());
        // Allow Enter key to submit
        passwordField.setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Preencha usuário e senha.");
            return;
        }

        try {
            User authenticatedUser = authController.authenticate(username, password);
            navigateToMainScreen(authenticatedUser);
        } catch (AuthenticationException ex) {
            showError("Usuário ou senha inválidos.");
            passwordField.clear();
        } catch (Exception ex) {
            showError("Erro ao autenticar. Tente novamente.");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void navigateToMainScreen(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/academic/system/view/javafx/MainScreen.fxml"));
            Scene scene = new Scene(loader.load(), 720, 520);
            scene.getStylesheets().add(
                    getClass().getResource("/org/example/academic/system/view/javafx/styles.css").toExternalForm());

            MainScreenController controller = loader.getController();
            controller.initializeWithUser(user);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setTitle("Academic System — " + user.getRole().name());
        } catch (Exception ex) {
            showError("Erro ao carregar tela principal.");
            ex.printStackTrace();
        }
    }
}
