package com.example.medicare_projekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Controller für das Hinzufügen neuer Medikamente.
 * Diese Klasse verwaltet die Benutzereingabe in den Textfeldern, validiert die Eingaben,
 * erstellt ein neues Medikament-Objekt und fügt es zum Medikamentenmodell hinzu.
 */
public class AddMedicationController {

    // Das Medikamentenmodell zum Speichern und Laden der Medikamente
    private MedicationModel medicationModel = new MedicationModel();

    // Der Controller der Medikamentenliste (wird verwendet, um die Liste nach dem Hinzufügen zu aktualisieren)
    private MedicationListController medicationListController;
    @FXML
    private Button speicherButton;
    @FXML
    private TextField medikamentTextFeld;
    @FXML
    private TextField indexTextFeld;
    @FXML
    private TextField nebenwirkungenTextFeld;

    /**
     * Initialisiert den Controller.
     * Lädt die Medikamentendaten aus der Datei.
     */
    public void initialize() {
        medicationModel = new MedicationModel(); // Modell initialisieren
        medicationModel.loadDataFromFile();     // Daten laden
    }

    /**
     * Behandelt das Klicken des Speicher-Buttons.
     * Liest die Eingaben aus den Textfeldern, validiert sie, erstellt ein neues
     * Medikament-Objekt und fügt es zum Modell hinzu.
     *
     * @param actionEvent Das auslösende Ereignis.
     */
    public void handleSpeicherButton(ActionEvent actionEvent) {

        // Einlesen der Medikamentendaten aus den Textfeldern
        String medikamentName = medikamentTextFeld.getText();
        String indexString = indexTextFeld.getText();
        String nebenwirkungenText = nebenwirkungenTextFeld.getText();
        String[] nebenwirkungenArray = nebenwirkungenText.split(",");
        ArrayList<String> nebenwirkungen = new ArrayList<>(Arrays.asList(nebenwirkungenArray));

        // Trimmen der Leerzeichen aus den Nebenwirkungen
        for (int i = 0; i < nebenwirkungen.size(); i++) {
            nebenwirkungen.set(i, nebenwirkungen.get(i).trim());
        }

        // Validierung der Eingaben
        if (medikamentName.isEmpty() || indexString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Bitte füllen Sie alle Felder aus.");
            alert.showAndWait();
            return;
        }

        // Konvertierung des Index und Überprüfung auf doppelte Medikamente
        try {
            int index = Integer.parseInt(indexString);

            if(medicationModel.getMedication().stream().anyMatch(medication -> medication.getMedicationName().equals(medikamentName) && medication.getIndex() == index)) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Fehler");
                alert2.setContentText("Medikament existiert bereits.");
                alert2.showAndWait();
                return;
            }

            // Erstellen des Medikament-Objekts und Hinzufügen zum Modell
            Medication newMedication = new Medication(medikamentName, index, nebenwirkungen);
            MedicationModel.addMedication(newMedication);

            // Aktualisieren der Medikamentenliste und Schließen des Fensters
            medicationListController.refreshMedicationList(); // Liste aktualisieren
            medikamentTextFeld.clear();
            indexTextFeld.clear();
            nebenwirkungenTextFeld.clear();

            Stage stage = (Stage) speicherButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            // Fehlerbehandlung für ungültigen Index
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Bitte geben Sie eine gültige Zahl für den Index ein.");
            alert.showAndWait();
        }
    }

    /**
     * Setzt den Controller der Medikamentenliste.
     *
     * @param controller Der Controller der Medikamentenliste.
     */
    public void setMedicationListController(MedicationListController controller) {
        this.medicationListController = controller;
    }
}
