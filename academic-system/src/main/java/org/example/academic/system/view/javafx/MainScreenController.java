package org.example.academic.system.view.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;

/**
 * TUS-2408 - Create JavaFX role-based main screen.
 *
 * Displays operations available to the authenticated user according to their role.
 * ADMIN sees all options; PROFESSOR sees only professor-related options.
 * Role-based visibility does NOT replace authorization checks in the service/controller layer.
 */
public class MainScreenController {

    @FXML private Label welcomeLabel;
    @FXML private Label roleLabel;

    // ADMIN-only buttons
    @FXML private Button registerClassButton;
    @FXML private Button persistenceConfigButton;
    @FXML private Button persistenceReportButton;

    // Common buttons
    @FXML private Button registerAssessmentButton;
    @FXML private Button classReportButton;
    @FXML private Button weightReportButton;
    @FXML private Button visualizeDataButton;
    @FXML private Button logoutButton;

    @FXML private VBox adminSection;

    private User currentUser;

    public void initializeWithUser(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Bem-vindo, " + user.getUsername());
        roleLabel.setText("Perfil: " + user.getRole().name());

        boolean isAdmin = user.getRole() == Role.ADMIN;
        adminSection.setVisible(isAdmin);
        adminSection.setManaged(isAdmin);
    }

    @FXML
    private void handleRegisterClass() {
        openScreen("/org/example/academic/system/view/javafx/ClassRegistrationScreen.fxml",
                "Cadastrar Turma", 480, 320, controller -> {
                    if (controller instanceof ClassRegistrationController c) {
                        c.initializeWithUser(currentUser);
                    }
                });
    }

    @FXML
    private void handleRegisterAssessment() {
        openScreen("/org/example/academic/system/view/javafx/AssessmentRegistrationScreen.fxml",
                "Cadastrar Avaliação", 520, 400, controller -> {
                    if (controller instanceof AssessmentRegistrationController c) {
                        c.initializeWithUser(currentUser);
                    }
                });
    }

    @FXML
    private void handleClassReport() {
        openScreen("/org/example/academic/system/view/javafx/ReportScreen.fxml",
                "Relatórios", 680, 480, controller -> {
                    if (controller instanceof ReportScreenController c) {
                        c.initializeWithUser(currentUser);
                        c.showClassSummaryReport();
                    }
                });
    }

    @FXML
    private void handleWeightReport() {
        openScreen("/org/example/academic/system/view/javafx/ReportScreen.fxml",
                "Relatório de Pesos", 680, 480, controller -> {
                    if (controller instanceof ReportScreenController c) {
                        c.initializeWithUser(currentUser);
                        c.showWeightReport();
                    }
                });
    }

    @FXML
    private void handlePersistenceConfig() {
        openScreen("/org/example/academic/system/view/javafx/PersistenceConfigScreen.fxml",
                "Configuração de Persistência", 440, 300, controller -> {
                    if (controller instanceof PersistenceConfigController c) {
                        c.initializeWithUser(currentUser);
                    }
                });
    }

    @FXML
    private void handlePersistenceReport() {
        openScreen("/org/example/academic/system/view/javafx/ReportScreen.fxml",
                "Relatório de Persistência", 680, 480, controller -> {
                    if (controller instanceof ReportScreenController c) {
                        c.initializeWithUser(currentUser);
                        c.showPersistenceReport();
                    }
                });
    }

    @FXML
    private void handleVisualizeData() {
        openScreen("/org/example/academic/system/view/javafx/VisualizationScreen.fxml",
                "Visualizar Dados", 720, 520, controller -> {
                    if (controller instanceof VisualizationScreenController c) {
                        c.initializeWithUser(currentUser);
                    }
                });
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/academic/system/view/javafx/LoginScreen.fxml"));
            Scene scene = new Scene(loader.load(), 420, 320);
            scene.getStylesheets().add(
                    getClass().getResource("/org/example/academic/system/view/javafx/styles.css").toExternalForm());

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Academic System");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openScreen(String fxmlPath, String title, int width, int height,
                            java.util.function.Consumer<Object> controllerSetup) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), width, height);
            scene.getStylesheets().add(
                    getClass().getResource("/org/example/academic/system/view/javafx/styles.css").toExternalForm());

            controllerSetup.accept(loader.getController());

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initOwner(logoutButton.getScene().getWindow());
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
