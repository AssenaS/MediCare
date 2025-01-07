package com.example.medicare_projekt;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Repräsentiert einen Patienten mit Namen, Geburtsdatum, Index und einer Liste von Erinnerungen.
 */
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private LocalDate birthday;
    private ArrayList<Integer> index;
    private List<Reminder> reminders;
    /**
     * Konstruktor für die Patient-Klasse.
     *
     * @param name Der Name des Patienten.
     * @param birthday Das Geburtsdatum des Patienten.
     * @param index Die Medikamenten-IDs, die dem Patienten zugeordnet sind.
     */
    public Patient(String name, LocalDate birthday, ArrayList<Integer> index) {
        this.name = name;
        this.birthday = birthday;
        this.index = index;
        this.reminders = new ArrayList<>();
    }
    // ... Getter und Setter für name, birthday, index, reminders
    public ArrayList<Integer> getIndex() {
        return index;
    }


    public LocalDate getBirthday() {
        return birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Patient: " + name + ", " + birthday + ", " + index;
    }
}
