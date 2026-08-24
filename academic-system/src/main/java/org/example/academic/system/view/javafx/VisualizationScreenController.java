package org.example.academic.system.view.javafx;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.academic.system.model.AcademicSystem;
import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;
import org.example.academic.system.model.User;

import java.util.List;

/**
 * TUS-2413 - Create JavaFX class and assessment visualization screen.
 *
 * Displays registered classes and their assessments in a tabular view.
 * Delegates data access to AcademicSystemController — no business logic in GUI layer.
 */
public class VisualizationScreenController {

    @FXML private TableView<AcademicClass> classTable;
    @FXML private TableColumn<AcademicClass, String> classCodeColumn;
    @FXML private TableColumn<AcademicClass, String> classTitleColumn;
    @FXML private TableColumn<AcademicClass, String> assessmentCountColumn;

    @FXML private TableView<Assessment> assessmentTable;
    @FXML private TableColumn<Assessment, String> assessmentTypeColumn;
    @FXML private TableColumn<Assessment, Double> assessmentValueColumn;
    @FXML private TableColumn<Assessment, Double> assessmentWeightColumn;

    @FXML private Label selectedClassLabel;

    private User currentUser;

    @FXML
    public void initialize() {
        // Class table columns
        classCodeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getCode()));
        classTitleColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getTitle()));
        assessmentCountColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        String.valueOf(data.getValue().getAssessments().size()) + " avaliação(ões)"));

        // Assessment table columns
        assessmentTypeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getType()));
        assessmentValueColumn.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getValue()).asObject());
        assessmentWeightColumn.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getWeight()).asObject());

        // Load classes
        List<AcademicClass> classes = AcademicSystem.getInstance().getClasses();
        classTable.setItems(FXCollections.observableArrayList(classes));

        // When a class is selected, load its assessments
        classTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        selectedClassLabel.setText(
                                "Avaliações de: " + newVal.getCode() + " — " + newVal.getTitle());
                        assessmentTable.setItems(
                                FXCollections.observableArrayList(newVal.getAssessments()));
                    } else {
                        selectedClassLabel.setText("Selecione uma turma para ver as avaliações");
                        assessmentTable.setItems(FXCollections.emptyObservableList());
                    }
                });

        selectedClassLabel.setText("Selecione uma turma para ver as avaliações");
    }

    public void initializeWithUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void handleRefresh() {
        List<AcademicClass> classes = AcademicSystem.getInstance().getClasses();
        classTable.setItems(FXCollections.observableArrayList(classes));
        assessmentTable.setItems(FXCollections.emptyObservableList());
        selectedClassLabel.setText("Selecione uma turma para ver as avaliações");
    }
}
