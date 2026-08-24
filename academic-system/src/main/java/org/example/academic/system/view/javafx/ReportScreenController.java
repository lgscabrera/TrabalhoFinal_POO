package org.example.academic.system.view.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import org.example.academic.system.model.AcademicSystem;
import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;

/**
 * TUS-2411 - Create JavaFX report screen.
 *
 * Allows generation of academic reports through the GUI.
 * ADMIN users additionally have access to the persistence configuration report.
 * Delegates report generation to AcademicSystemController.
 */
public class ReportScreenController {

    @FXML private TextArea reportArea;
    @FXML private Button classSummaryButton;
    @FXML private Button weightReportButton;
    @FXML private Button persistenceReportButton;
    @FXML private HBox adminReportBar;

    private AcademicSystemController academicController;
    private User currentUser;

    @FXML
    public void initialize() {
        academicController = new AcademicSystemController(AcademicSystem.getInstance());
        reportArea.setEditable(false);
        reportArea.setWrapText(true);
    }

    public void initializeWithUser(User user) {
        this.currentUser = user;
        boolean isAdmin = user.getRole() == Role.ADMIN;
        adminReportBar.setVisible(isAdmin);
        adminReportBar.setManaged(isAdmin);
    }

    public void showClassSummaryReport() {
        String report = academicController.generateClassSummaryReport(currentUser);
        reportArea.setText(report);
    }

    public void showWeightReport() {
        String report = academicController.generateWeightReport(currentUser);
        reportArea.setText(report);
    }

    public void showPersistenceReport() {
        String report = academicController.generatePersistenceReport(currentUser);
        reportArea.setText(report);
    }

    @FXML
    private void handleClassSummary() {
        showClassSummaryReport();
    }

    @FXML
    private void handleWeightReport() {
        showWeightReport();
    }

    @FXML
    private void handlePersistenceReport() {
        showPersistenceReport();
    }
}
