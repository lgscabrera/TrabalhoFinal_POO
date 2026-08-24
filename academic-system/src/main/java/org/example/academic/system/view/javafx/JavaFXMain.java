package org.example.academic.system.view.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.academic.system.model.AcademicSystem;

/**
 * TUS-2406 - Entry point da interface gráfica JavaFX.
 */
public class JavaFXMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AcademicSystem.getInstance();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/academic/system/view/javafx/LoginScreen.fxml"));
        Scene scene = new Scene(loader.load(), 420, 320);
        scene.getStylesheets().add(
                getClass().getResource("/org/example/academic/system/view/javafx/styles.css")
                          .toExternalForm());

        primaryStage.setTitle("Academic System");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
