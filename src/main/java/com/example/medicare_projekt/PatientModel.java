package com.example.medicare_projekt;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PatientModel {
    private static final String patientFile = "patient.ser";

    private static ArrayList<Patient> patientList = new ArrayList<>();

    public static ArrayList<Patient> getPatients() {
        return patientList;
    }

    public void patientSerialize() {
        try (FileOutputStream fileOut = new FileOutputStream(patientFile);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(patientList);
            System.out.println("Serialized Patient in List!");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void addPatient(String name, LocalDate birthday, ArrayList<Integer> index) {
        patientList.add(new Patient(name, birthday, index));

        System.out.println(patientList);
    }

    public void loadDataFromFile() {
        File file = new File(patientFile);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                patientList = (ArrayList<Patient>) in.readObject();
                System.out.println("Daten geladen aus " + patientFile);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Datei nicht gefunden: " + patientFile);
        }
    }

}
