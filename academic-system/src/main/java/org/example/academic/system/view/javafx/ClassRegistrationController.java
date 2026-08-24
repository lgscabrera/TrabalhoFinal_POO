package org.example.academic.system.view.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.academic.system.model.AcademicSystem;
import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.exception.AcademicSystemException;
import org.example.academic.system.exception.AuthorizationException;
import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;

/**
 * TUS-2409 - Create JavaFX class registration screen.
 *
 * Provides a form for ADMIN users to register academic classes.
 * Delegates to AcademicSystemController — no business logic in the GUI layer.
 */
public class ClassRegistrationController {

    @FXML private TextField classCodeField;
    @FXML private TextField classTitleField;
    @FXML private Button registerButton;
    @FXML private Label feedbackLabel;

    private AcademicSystemController academicController;
    private User currentUser;

    public void initializeWithUser(User user) {
        this.currentUser = user;
        if (user.getRole() != Role.ADMIN) {
            feedbackLabel.setText("Acesso negado: apenas administradores podem cadastrar turmas.");
            feedbackLabel.getStyleClass().add("error");
            registerButton.setDisable(true);
        }
    }

    @FXML
    public void initialize() {
        feedbackLabel.setVisible(false);
        academicController = new AcademicSystemController(AcademicSystem.getInstance());
    }

    @FXML
    private void handleRegister() {
        String code = classCodeField.getText().trim();
        String title = classTitleField.getText().trim();

        if (code.isEmpty() || title.isEmpty()) {
            showFeedback("Preencha todos os campos.", true);
            return;
        }

        try {
            academicController.registerClass(currentUser, code, title);
            showFeedback("Turma \"" + title + "\" cadastrada com sucesso!", false);
            classCodeField.clear();
            classTitleField.clear();
        } catch (AuthorizationException ex) {
            showFeedback("Acesso negado: " + ex.getMessage(), true);
        } catch (AcademicSystemException ex) {
            showFeedback("Erro: " + ex.getMessage(), true);
        }
    }

    @FXML
    private void handleClear() {
        classCodeField.clear();
        classTitleField.clear();
        feedbackLabel.setVisible(false);
    }

    private void showFeedback(String message, boolean isError) {
        feedbackLabel.setText(message);
        feedbackLabel.getStyleClass().removeAll("success", "error");
        feedbackLabel.getStyleClass().add(isError ? "error" : "success");
        feedbackLabel.setVisible(true);
    }
}
