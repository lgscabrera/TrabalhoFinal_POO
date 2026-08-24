package org.example.academic.system.view.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.example.academic.system.model.AcademicSystem;
import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.exception.AuthorizationException;
import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;

/**
 * TUS-2412 - Create JavaFX persistence configuration screen.
 *
 * Allows ADMIN users to configure the persistence type (TXT, XML, JSON).
 * Delegates to AcademicSystemController — no business logic in the GUI layer.
 */
public class PersistenceConfigController {

    @FXML private RadioButton txtRadio;
    @FXML private RadioButton xmlRadio;
    @FXML private RadioButton jsonRadio;
    @FXML private Button confirmButton;
    @FXML private Label feedbackLabel;

    private final ToggleGroup persistenceGroup = new ToggleGroup();
    private AcademicSystemController academicController;
    private User currentUser;

    @FXML
    public void initialize() {
        feedbackLabel.setVisible(false);
        academicController = new AcademicSystemController(AcademicSystem.getInstance());

        txtRadio.setToggleGroup(persistenceGroup);
        xmlRadio.setToggleGroup(persistenceGroup);
        jsonRadio.setToggleGroup(persistenceGroup);
        txtRadio.setSelected(true);
    }

    public void initializeWithUser(User user) {
        this.currentUser = user;
        if (user.getRole() != Role.ADMIN) {
            confirmButton.setDisable(true);
            feedbackLabel.setText("Acesso negado: apenas administradores podem configurar a persistência.");
            feedbackLabel.getStyleClass().add("error");
            feedbackLabel.setVisible(true);
        }
    }

    @FXML
    private void handleConfirm() {
        RadioButton selected = (RadioButton) persistenceGroup.getSelectedToggle();
        if (selected == null) {
            showFeedback("Selecione um tipo de persistência.", true);
            return;
        }

        String type = selected.getText().toUpperCase();

        try {
            academicController.configurePersistence(currentUser, type);
            showFeedback("Persistência configurada para " + type + " com sucesso.", false);
        } catch (AuthorizationException ex) {
            showFeedback("Acesso negado: " + ex.getMessage(), true);
        } catch (Exception ex) {
            showFeedback("Erro ao configurar persistência: " + ex.getMessage(), true);
        }
    }

    private void showFeedback(String message, boolean isError) {
        feedbackLabel.setText(message);
        feedbackLabel.getStyleClass().removeAll("success", "error");
        feedbackLabel.getStyleClass().add(isError ? "error" : "success");
        feedbackLabel.setVisible(true);
    }
}
