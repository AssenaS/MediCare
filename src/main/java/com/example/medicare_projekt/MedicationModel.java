package com.example.medicare_projekt;

import java.io.*;
import java.util.ArrayList;

/**
 * Diese Klasse repräsentiert ein Modell für die Verwaltung von Medikamentendaten.
 * Sie lädt Medikamente aus einer Datei, ermöglicht das Hinzufügen neuer Medikamente
 * und speichert die Daten persistent.
 */
public class MedicationModel {

    /**
     * Der Dateiname, unter dem die Medikamentendaten serialisiert werden.
     */
    private static final String medicationFile = "medication.ser";

    /**
     * Die Liste aller Medikamente.
     */
    private static ArrayList<Medication> medicationList = new ArrayList<>();

    /**
     * Gibt die Liste aller Medikamente zurück.
     *
     * @return Die Liste der Medikamente.
     */
    public static ArrayList<Medication> getMedication() {
        return medicationList;
    }

    /**
     * Konstruktor: Lädt Medikamentendaten aus der Datei beim Erstellen des Modells.
     */
    public MedicationModel() {
        loadDataFromFile();  // Daten werden beim Erstellen des Modells geladen
    }

    /**
     * Serialisiert die Liste der Medikamente in eine Datei.
     * Diese Methode speichert die aktuellen Medikamentendaten persistent.
     */
    public static void medicationSerialize() {
        try (FileOutputStream fileOut = new FileOutputStream(medicationFile);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(medicationList);
            System.out.println("Serialized Medication in List!"); // Erfolgsmeldung (optional)

        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Fügt ein neues Medikament zur Liste hinzu und serialisiert die Liste.
     *
     * @param medication Das hinzuzufügende Medikament.
     */
    public static void addMedication(Medication medication) {
        medicationList.add(medication);

        System.out.println(medicationList); // Ausgabe zur Kontrolle
        medicationSerialize(); // Speichern der aktualisierten Daten
    }

    /**
     * Lädt die Medikamentendaten aus der Datei.
     * Falls die Datei nicht existiert oder beim Lesen Fehler auftreten, wird eine leere Liste verwendet.
     */
    public void loadDataFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(medicationFile))) {
            medicationList = (ArrayList<Medication>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            medicationList = new ArrayList<>();
        }
    }
}
