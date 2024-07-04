package com.example.medicare_projekt;

import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MedicationListController {
    MedicationModel medicationModel;

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

    private Scene previousScene = SceneManager.getInstance().getPreviousScene();
    private Stage stage = null;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    public void handleMedikamentHinzufuegenButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MedikamentHinzufuegen.fxml"));
        Parent MedikamenteHinzufuegen = loader.load();
        Scene scene = new Scene(MedikamenteHinzufuegen);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Medikament hinzufügen");
        stage.show();

        AddMedicationController controller = loader.getController();
        controller.setMedicationListController(this);
        stage.show();

    }

    @FXML
    public void initialize() {
        medicationModel = new MedicationModel();
        medicationModel.loadDataFromFile();
        medicationArrayList = medicationModel.getMedication();
        updateTableView();
    }

    @FXML
    private void updateTableView() {
        tabelleMedikamente.getItems().clear();
        tabelleMedikamente.getItems().addAll(medicationArrayList);

        tabelleMedikament.setCellValueFactory(new PropertyValueFactory<>("medicationName"));
        tabelleMedikamentenIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
        tabelleNebenwirkungen.setCellValueFactory(new PropertyValueFactory<>("nebenwirkungen"));
    }

    public void refreshMedicationList() {
        medicationArrayList = medicationModel.getMedication();
        updateTableView();
    }

    public void handleMedicationDelete(ActionEvent actionEvent) {
        Medication selectedMedication = (Medication) tabelleMedikamente.getSelectionModel().getSelectedItem();
        medicationArrayList.remove(selectedMedication);
        MedicationModel.getMedication().remove(selectedMedication);
        MedicationModel.medicationSerialize();
        updateTableView();

    }
}
