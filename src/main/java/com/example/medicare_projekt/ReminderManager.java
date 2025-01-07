package com.example.medicare_projekt;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Verwaltet Erinnerungen und zeigt sie zu den geplanten Zeiten an.
 */
public class ReminderManager {
    private List<Reminder> reminders = new ArrayList<>();
    private Timer timer = new Timer(true);
    /**
     * Fügt eine Erinnerung hinzu und plant sie.
     *
     * @param reminder Die hinzuzufügende Erinnerung.
     */
    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
        scheduleReminder(reminder);
    }
    /**
     * Plant die Ausführung einer Erinnerung.
     *
     * @param reminder Die zu planende Erinnerung.
     */
    private void scheduleReminder(Reminder reminder) {
        long delay = reminder.getReminderTime().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli() - System.currentTimeMillis();

        // Planung der Erinnerung mit dem Timer
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Ausführung im JavaFX-Thread, um das Erinnerungsfenster anzuzeigen
                Platform.runLater(() -> showReminderWindow(reminder.getMessage(), reminder.getReminderTime()));
            }
        }, delay);
    }
    /**
     * Zeigt ein Erinnerungsfenster mit der angegebenen Nachricht und Erinnerungszeit an.
     *
     * @param message     Die Nachricht, die im Erinnerungsfenster angezeigt werden soll.
     * @param reminderTime Die geplante Erinnerungszeit.
     */
    private void showReminderWindow(String message, LocalDateTime reminderTime) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Medikamenten-Erinnerung");
        alert.setHeaderText(null);
        alert.setContentText(message + " um " + reminderTime);
        alert.showAndWait();
    }
}