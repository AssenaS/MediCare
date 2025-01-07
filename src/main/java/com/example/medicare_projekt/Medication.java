package com.example.medicare_projekt;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Repräsentiert ein Medikament mit Namen, Index und einer Liste von Nebenwirkungen.
 * Diese Klasse implementiert Serializable, um die Speicherung und das Laden von Medikamentenobjekten zu ermöglichen.
 */
public class Medication implements Serializable {


    Patient patient;

    private String medicationName;
    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private ArrayList<String> nebenwirkungen;
    public ArrayList<String> getNebenwirkungen() {
        return nebenwirkungen;
    }

    public void setNebenwirkungen(ArrayList<String> nebenwirkungen) {
        this.nebenwirkungen = nebenwirkungen;
    }

    /**
     * Konstruktor neues Medikament-Objekts.
     *
     * @param medicationName Der Name des Medikaments.
     * @param index         Ein eindeutiger Index (optional) zur Identifikation des Medikaments.
     * @param nebenwirkungen Eine ArrayList mit den möglichen Nebenwirkungen des Medikaments.
     */
    public Medication(String medicationName, int index, ArrayList<String> nebenwirkungen){
        this.medicationName = medicationName;
        this.index = index;
        this.nebenwirkungen = nebenwirkungen;
    }
    /**
     * Gibt eine String-Darstellung des Medikaments zurück, die Name, Index und Nebenwirkungen enthält.
     *
     * @return Eine formatierte Zeichenkette mit den Informationen über das Medikament.
     */
    @Override
    public String toString() {
        return "Medikament: " + medicationName + ", Index: " + index + ", Nebenwirkungen: " + nebenwirkungen;
    }


}
