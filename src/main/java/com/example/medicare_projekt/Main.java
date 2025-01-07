package com.example.medicare_projekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Die Hauptklasse der Anwendung, die den Startpunkt für die JavaFX-Anwendung darstellt.
 */
public class Main extends Application {

    /**
     * Die `start`-Methode wird automatisch aufgerufen, wenn die JavaFX-Anwendung gestartet wird.
     * Sie lädt den Login-View und zeigt ihn an.
     *
     * @param primaryStage Die Hauptbühne der Anwendung.
     * @throws IOException Wenn ein Fehler beim Laden der FXML-Datei auftritt.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInView.fxml"));
        AnchorPane root = loader.load();

        LogInController controller = loader.getController();
        controller.setStage(primaryStage);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Die `main`-Methode ist der Einstiegspunkt für die Anwendung.
     * Sie startet die JavaFX-Anwendung.
     *
     * @param args Die Befehlszeilenargumente.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
