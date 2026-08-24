package org.example.academic.system.view.javafx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.academic.system.model.AcademicSystem;
import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.exception.AcademicSystemException;
import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TUS-2410 - Create JavaFX assessment registration screen.
 *
 * Allows professors and admins to register assessments in existing classes.
 * Delegates to AcademicSystemController — no business logic in the GUI layer.
 */
public class AssessmentRegistrationController {

    @FXML private ComboBox<String> classComboBox;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField valueField;
    @FXML private TextField weightField;
    @FXML private Button registerButton;
    @FXML private Label feedbackLabel;

    private AcademicSystemController academicController;
    private User currentUser;

    @FXML
    public void initialize() {
        feedbackLabel.setVisible(false);
        academicController = new AcademicSystemController(AcademicSystem.getInstance());

        typeComboBox.setItems(FXCollections.observableArrayList(
                "EXAM", "PRACTICAL_ASSIGNMENT", "SEMINAR", "ASSIGNMENT"));
        typeComboBox.getSelectionModel().selectFirst();

        loadClasses();
    }

    public void initializeWithUser(User user) {
        this.currentUser = user;
    }

    private void loadClasses() {
        List<AcademicClass> classes = AcademicSystem.getInstance().getClasses();
        List<String> classCodes = classes.stream()
                .map(AcademicClass::getCode)
                .collect(Collectors.toList());
        classComboBox.setItems(FXCollections.observableArrayList(classCodes));
        if (!classCodes.isEmpty()) {
            classComboBox.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void handleRegister() {
        String classCode = classComboBox.getValue();
        String type = typeComboBox.getValue();
        String valueText = valueField.getText().trim();
        String weightText = weightField.getText().trim();

        if (classCode == null || type == null || valueText.isEmpty() || weightText.isEmpty()) {
            showFeedback("Preencha todos os campos.", true);
            return;
        }

        try {
            double value = Double.parseDouble(valueText);
            double weight = Double.parseDouble(weightText);

            academicController.registerAssessment(currentUser, classCode, type, value, weight);
            showFeedback("Avaliação cadastrada com sucesso na turma " + classCode + "!", false);
            valueField.clear();
            weightField.clear();
        } catch (NumberFormatException ex) {
            showFeedback("Valor e peso devem ser números válidos.", true);
        } catch (AcademicSystemException ex) {
            showFeedback("Erro: " + ex.getMessage(), true);
        }
    }

    @FXML
    private void handleClear() {
        valueField.clear();
        weightField.clear();
        feedbackLabel.setVisible(false);
        if (!classComboBox.getItems().isEmpty()) {
            classComboBox.getSelectionModel().selectFirst();
        }
        typeComboBox.getSelectionModel().selectFirst();
    }

    private void showFeedback(String message, boolean isError) {
        feedbackLabel.setText(message);
        feedbackLabel.getStyleClass().removeAll("success", "error");
        feedbackLabel.getStyleClass().add(isError ? "error" : "success");
        feedbackLabel.setVisible(true);
    }
}
