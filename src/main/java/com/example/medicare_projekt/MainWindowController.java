package com.example.medicare_projekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalTime;
/**
 * Hauptcontroller für die Anwendung.
 * Dieser Controller verwaltet die Hauptansicht, einschließlich der Anzeige von Patienten in
 * einer Tabelle, dem Hinzufügen neuer Patienten, dem Setzen von Erinnerungen für Patienten,
 * dem Anzeigen der Medikamentenliste und dem Entfernen von Patienten.
 */
public class MainWindowController {
    private Stage stage;
    private Scene hauptScene;
    PatientModel patientModel;
    MedicationListController medicationListController;

    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private Button addButton;

    @FXML
    private Button viewMedicationListButton;

    @FXML
    private TextField PatientenNameTextFeld;

    @FXML
    private DatePicker GeburtsdatumFeld;

    @FXML
    private TextField indexLabel;

    @FXML
    private AnchorPane hauptFenster;

    @FXML
    private TableView<Patient> tabelle;

    @FXML
    private TableColumn<Patient, String> tabelleName;

    @FXML
    private TableColumn<Patient, LocalDate> tabelleGeburtsdatum;

    @FXML
    private TableColumn<Patient, Integer> tabelleIndex;

    private ArrayList<Patient> arrayPatientList;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<Integer> hourComboBox;

    @FXML
    private ComboBox<Integer> minuteComboBox;

    @FXML
    private Label reminderLabel;

    private ReminderManager reminderManager = new ReminderManager();


    /**
     * Initialisiert den Controller.
     *
     * Lädt die Patientendaten aus der Datei, initialisiert das Modell, ruft die
     * Patientenliste ab, befüllt die Tabellenansicht und initialisiert die ComboBoxen
     * für die Erinnerungsfunktion.
     */
    public void initialize() {
        patientModel = new PatientModel();
        patientModel.loadDataFromFile();
        arrayPatientList = PatientModel.getPatients();

        if (tabelleName != null) {
            tabelleName.setCellValueFactory(new PropertyValueFactory<>("name"));
        }
        if (tabelleGeburtsdatum != null) {
            tabelleGeburtsdatum.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        }
        if (tabelleIndex != null) {
            tabelleIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
        }
        updateTableViewPatient();

        for (int i = 0; i < 24; i++) {
            hourComboBox.getItems().add(i);
        }
        for (int i = 0; i < 60; i += 1) {
            minuteComboBox.getItems().add(i);
        }
    }
    /**
     * Behandelt das Klicken des "Patient hinzufügen"-Buttons.
     *
     * Liest die Eingaben aus den Textfeldern, validiert sie, erstellt einen neuen
     * Patienten und fügt ihn zur Liste hinzu. Speichert die Patientenliste und aktualisiert
     * die Tabellenansicht.
     *
     * @param event Das auslösende ActionEvent.
     */
    @FXML
    private void handlePatientHinzufuegenButton(ActionEvent event) {
        String name = PatientenNameTextFeld.getText();
        LocalDate birthday = GeburtsdatumFeld.getValue();
        String indexString = indexLabel.getText();

        if(indexString == null || indexString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText(null);
            alert.setContentText("Bitte geben Sie einen Index ein.");
            alert.showAndWait();
            return;
        }
        if (name != null && !name.isEmpty() && birthday != null && indexString != null) {
            ArrayList<Integer> medikamenteIndex = new ArrayList<>();
            String[] indexStrArray = indexString.split(",");
            for (int i = 0; i < indexStrArray.length; i++) {
                try {
                    int index = Integer.parseInt(indexStrArray[i].trim());
                    medikamenteIndex.add(index);
                } catch (NumberFormatException e) {
                    System.out.println("Fehler beim Hinzufügen des Patienten");
                }
            }

            if(patientModel.getPatients().stream().anyMatch(patient -> patient.getName().equals(name) && patient.getBirthday().equals(birthday))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setHeaderText(null);
                alert.setContentText("Patient existiert bereits.");
                alert.showAndWait();
                return;
            }

            patientModel.addPatient(name, birthday, medikamenteIndex);
            patientModel.patientSerialize();
            PatientenNameTextFeld.clear();
            GeburtsdatumFeld.setValue(null);
            indexLabel.clear();
            updateTableViewPatient();
        }
    }
    /**
     * Setzt eine Erinnerung für den ausgewählten Patienten.
     *
     * Liest das Datum und die Uhrzeit aus den entsprechenden Bedienelementen, erstellt eine Erinnerung
     * und fügt sie dem Erinnerungsmanager hinzu. Zeigt eine Bestätigung oder Fehlermeldung an.
     *
     * @param event Das auslösende ActionEvent.
     */
    @FXML
    private void handleSetReminder(ActionEvent event) {
        LocalDate date = datePicker.getValue();
        Integer hour = hourComboBox.getValue();
        Integer minute = minuteComboBox.getValue();

        if (date == null || hour == null || minute == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Fehler");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Bitte wählen Sie einen Patienten, Datum, Stunde und Minute.");
            errorAlert.showAndWait();
            return;
        }

        LocalDateTime reminderTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
        Patient selectedPatient = tabelle.getSelectionModel().getSelectedItem();


        if (selectedPatient != null) {
            String message = "Erinnerung für " + selectedPatient.getName() + ": Medikamente einnehmen";

            reminderManager.addReminder(new Reminder(message, reminderTime));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erinnerung");
            alert.setHeaderText(null);
            alert.setContentText("Erinnerung gesetzt für: " + selectedPatient + ", um: " + reminderTime);
            alert.showAndWait();

            datePicker.setValue(null);
            hourComboBox.setValue(null);
            minuteComboBox.setValue(null);

        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Fehler");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Bitte wählen Sie einen Patienten, Datum, Stunde und Minute.");
            errorAlert.showAndWait();
        }
    }
    /**
     * Aktualisiert die Tabellenansicht der Patienten.
     *
     * Leert die Tabelle, fügt die aktuellen Patientendaten hinzu und gibt eine Meldung aus,
     * falls nicht alle Felder ausgefüllt sind.
     */
    @FXML
    private void updateTableViewPatient() {
        if (tabelleName != null && tabelleGeburtsdatum != null && tabelleIndex != null) {
            tabelle.getItems().clear();
            tabelle.getItems().addAll(PatientModel.getPatients());
        }
        else {
            System.out.println("Alle Felder müssen ausgefüllt sein");
        }
    }
    /**
     * Öffnet die Medikamentenliste in einem neuen Fenster.
     *
     * @param actionEvent Das auslösende ActionEvent.
     * @throws IOException Wenn ein Fehler beim Laden der FXML-Datei auftritt.
     */
    public void handleMedikamentenListeAnsehen(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MedikamentAnsicht.fxml"));
        Parent medikamentenAnsicht = loader.load();
        Scene scene = new Scene(medikamentenAnsicht);

        SceneManager.getInstance().setPreviousScene(((Node) actionEvent.getSource()).getScene());

        Stage medicationStage = new Stage();
        medicationStage.setScene(scene);
        medicationStage.setTitle("Medikamentenliste");

        MedicationListController controller = loader.getController();
        controller.setStage(stage);
        controller.setPreviousScene(SceneManager.getInstance().getPreviousScene());

        double mainWindowX = stage.getX();
        double mainWindowY = stage.getY();
        double mainStageWidth = stage.getWidth();

        double medicationStageX = mainWindowX + mainStageWidth + 10;
        double medicationStageY = mainWindowY;

        medicationStage.setX(medicationStageX);
        medicationStage.setY(medicationStageY);

        medicationStage.show();
    }

    public void setMainScene(Scene hauptseiteScene) {
        this.scene = scene;
    }


    public void handleMedikamenteReminder(MouseEvent mouseEvent) {
        Patient selectedPatient = (Patient) tabelle.getSelectionModel().getSelectedItem();
    }

    /**
     * Entfernt den ausgewählten Patienten aus der Liste und speichert die Änderungen.
     *
     * @param actionEvent Das auslösende ActionEvent.
     */
    public void handlePatientRemove(ActionEvent actionEvent) {
        Patient selectedPatient = (Patient) tabelle.getSelectionModel().getSelectedItem();
        arrayPatientList.remove(selectedPatient);
        PatientModel.getPatients().remove(selectedPatient);
        patientModel.patientSerialize();
        updateTableViewPatient();
    }
}
