package com.example.medicare_projekt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

public class LogInModel {

    private static final String logInFile = "login.ser";


    public void logInSerialize() {
        try (FileOutputStream fileOut = new FileOutputStream(logInFile);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(logInFile);
            System.out.println("Serialized User in List!");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }



}
