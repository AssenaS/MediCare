package com.example.medicare_projekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Der Controller für den Login-View.
 * Er ist verantwortlich für die Verarbeitung der Benutzereingaben und die Navigation zum Hauptfenster.
 */
public class LogInController {

    // ... FXML Elemente

    @FXML
    private TextField benutzerNameTextField;

    @FXML
    private TextField passwortTextField;

    @FXML
    private Button signInButton;

    @FXML
    private ImageView imageView;

    private Stage stage;

    private Scene scene;

    private String passwort = "1234";

    private String benutzername = "demo";
    /**
     * Setzt die Stage für den Controller.
     *
     * @param stage Die Stage, die dem Controller zugeordnet werden soll.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainScene(Scene hauptseiteScene) {
        this.scene = scene;
    }

    /**
     * Behandelt das Klicken auf den "Sign in"-Button.
     * Überprüft die eingegebenen Anmeldeinformationen und öffnet das Hauptfenster bei Erfolg.
     *
     * @param event Das ActionEvent, das ausgelöst wurde.
     * @throws IOException Wenn ein Fehler beim Laden der FXML-Datei für das Hauptfenster auftritt.
     */
    public void handleSigninButton(ActionEvent event) throws IOException {
        // ... (Logik zur Überprüfung von Benutzername und Passwort, dann Öffnen des Hauptfensters)

        String passwortEingegeben = passwortTextField.getText();
        String benutzernameEingegeben = benutzerNameTextField.getText();

        if (benutzernameEingegeben.equals(benutzername) && passwortEingegeben.equals(passwort)) {
            Stage loginStage =
                    (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("HauptFenster.fxml"));
            Parent hauptFenster = loader.load();
            Scene scene = new Scene(hauptFenster);
            Stage mainStage = new Stage();
            mainStage.setScene(scene);
            mainStage.setTitle("MediCare");
            mainStage.show();

            MainWindowController controller = loader.getController();
            controller.setStage(mainStage);
            controller.setMainScene(scene);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Fehler");
            alert.setHeaderText(null);
            alert.setContentText("Falscher Benutzername oder Passwort!");
            alert.showAndWait();
        }
    }
}
