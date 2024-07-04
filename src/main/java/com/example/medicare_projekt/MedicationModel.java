package com.example.medicare_projekt;

import java.io.*;
import java.util.ArrayList;

public class MedicationModel {
    private static final String medicationFile = "medication.ser";

    private static ArrayList<Medication> medicationList = new ArrayList<>();

    public static ArrayList<Medication> getMedication() {
        return medicationList;
    }

    public MedicationModel() {
        medicationList = new ArrayList<>();
        loadDataFromFile();
    }
    public static void medicationSerialize() {
        try (FileOutputStream fileOut = new FileOutputStream(medicationFile);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(medicationList);
            System.out.println("Serialized Medication in List!");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void addMedication(Medication medication ){
        medicationList.add(medication);

        System.out.println(medicationList);
        medicationSerialize();

    }

    public void loadDataFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(medicationFile))) {
            medicationList = (ArrayList<Medication>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            medicationList = new ArrayList<>();
        }
    }

}
