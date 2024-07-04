package com.example.medicare_projekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class AddMedicationController {
    MedicationModel medicationModel = new MedicationModel();
    MedicationListController medicationListController;

    @FXML
    private Button speicherButton;

    @FXML
    private TextField medikamentTextFeld;

    @FXML
    private TextField indexTextFeld;

    @FXML
    private TextField nebenwirkungenTextFeld;

    public void initialize() {
        medicationModel = new MedicationModel();
        medicationModel.loadDataFromFile();
    }

    public void handleSpeicherButton(ActionEvent actionEvent) {
        String medikamentName = medikamentTextFeld.getText();
        String indexString = indexTextFeld.getText();
        String nebenwirkungenText = nebenwirkungenTextFeld.getText();

        String[] nebenwirkungenArray = nebenwirkungenText.split(",");
        ArrayList<String> nebenwirkungen = new ArrayList<>(Arrays.asList(nebenwirkungenArray));

        for (int i = 0; i < nebenwirkungen.size(); i++) {
            nebenwirkungen.set(i, nebenwirkungen.get(i).trim());
        }

        if (medikamentName.isEmpty() || indexString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText(null);
            alert.setContentText("Bitte füllen Sie alle Felder aus.");
            alert.showAndWait();
            return;
        }

        try {
            int index = Integer.parseInt(indexString);

            if(medicationModel.getMedication().stream().anyMatch(medication -> medication.getMedicationName().equals(medikamentName) && medication.getIndex() == index)) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Fehler");
                alert2.setHeaderText(null);
                alert2.setContentText("Medikament existiert bereits.");
                alert2.showAndWait();
                return;
            }

            Medication newMedication = new Medication(medikamentName,index,nebenwirkungen);
            MedicationModel.addMedication(newMedication);
            medicationModel.medicationSerialize();
            medicationListController.refreshMedicationList();

            medikamentTextFeld.clear();
            indexTextFeld.clear();
            nebenwirkungenTextFeld.clear();

            Stage stage = (Stage) speicherButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText(null);
            alert.setContentText("Bitte geben Sie eine gültige Zahl für den Index ein.");
            alert.showAndWait();
        }
    }

    public void setMedicationListController(MedicationListController controller) {
        this.medicationListController = controller;
    }

}

