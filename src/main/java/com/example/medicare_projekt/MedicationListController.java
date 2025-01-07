package com.example.medicare_projekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller für die Anzeige und Verwaltung einer Liste von Medikamenten.
 *
 * Dieser Controller ermöglicht das Anzeigen der Medikamente in einer Tabelle, das Hinzufügen
 * neuer Medikamente über ein separates Fenster, das Aktualisieren der Liste und das Löschen
 * von Medikamenten.
 */
public class MedicationListController {

    // Das Medikamentenmodell, das die Daten hält
    private MedicationModel medicationModel;

    // Liste der anzuzeigenden Medikamente
    private ArrayList<Medication> medicationArrayList;

    @FXML
    private Button zurueckButton;
    @FXML
    private Button medikamentHinzufuegen;
    @FXML
    private TableColumn<Medication, Integer> tabelleMedikamentenIndex;
    @FXML
    private TableColumn<Medication, String> tabelleMedikament;
    @FXML
    private TableColumn<Medication, String> tabelleNebenwirkungen;
    @FXML
    private TableView<Medication> tabelleMedikamente;

    private Scene previousScene;
    private Stage stage;

    public void setStage(Stage stage) { this.stage = stage; }
    public void setPreviousScene(Scene scene) { this.previousScene = scene; }

    /**
     * Initialisiert den Controller.
     *
     * Lädt die Medikamentendaten aus der Datei, initialisiert das Modell, ruft die
     * Medikamentenliste ab und aktualisiert die Tabellenansicht.
     */
    @FXML
    public void initialize() {
        medicationModel = new MedicationModel();
        medicationModel.loadDataFromFile();
        medicationArrayList = medicationModel.getMedication();
        updateTableView();
    }

    /**
     * Behandelt das Klicken des "Medikament hinzufügen"-Buttons.
     *
     * Öffnet ein neues Fenster zum Hinzufügen eines Medikaments.
     *
     * @param actionEvent Das auslösende Ereignis.
     * @throws IOException Wenn ein Fehler beim Laden der FXML-Datei auftritt.
     */
    public void handleMedikamentHinzufuegenButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MedikamentHinzufuegen.fxml"));
        Parent MedikamenteHinzufuegen = loader.load();
        Scene scene = new Scene(MedikamenteHinzufuegen);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Medikament hinzufügen");

        AddMedicationController controller = loader.getController();
        controller.setMedicationListController(this); // Referenz zu diesem Controller übergeben
        stage.show();
    }

    /**
     * Aktualisiert die Tabellenansicht mit den aktuellen Medikamentendaten.
     * Leert die Tabelle, füllt sie mit den Daten aus der Medikamentenliste und
     * verknüpft die Spalten mit den entsprechenden Eigenschaften der Medikamentenobjekte.
     */
    private void updateTableView() {
        tabelleMedikamente.getItems().clear();
        tabelleMedikamente.getItems().addAll(medicationArrayList);

        tabelleMedikament.setCellValueFactory(new PropertyValueFactory<>("medicationName"));
        tabelleMedikamentenIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
        tabelleNebenwirkungen.setCellValueFactory(new PropertyValueFactory<>("nebenwirkungen"));
    }

    /**
     * Aktualisiert die Medikamentenliste und die Tabellenansicht.
     *
     * Diese Methode wird aufgerufen, nachdem ein neues Medikament hinzugefügt wurde, um die
     * Liste und die Anzeige zu aktualisieren.
     */
    public void refreshMedicationList() {
        medicationArrayList = medicationModel.getMedication();
        updateTableView();
    }

    /**
     * Behandelt das Löschen eines Medikaments.
     *
     * @param actionEvent Das auslösende Ereignis.
     */
    public void handleMedicationDelete(ActionEvent actionEvent) {
        Medication selectedMedication = tabelleMedikamente.getSelectionModel().getSelectedItem();
        if (selectedMedication != null) {
            medicationArrayList.remove(selectedMedication);
            MedicationModel.getMedication().remove(selectedMedication);
            MedicationModel.medicationSerialize();
            updateTableView();
        }
    }
}
