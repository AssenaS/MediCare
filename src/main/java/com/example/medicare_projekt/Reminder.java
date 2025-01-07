package com.example.medicare_projekt;

import java.time.LocalDateTime;

/**
 * Stellt eine Erinnerung mit einer Nachricht und einer Erinnerungszeit dar.
 */
public class Reminder {
    private String message;
    private LocalDateTime reminderTime;
    /**
     * Konstruktor für die Reminder-Klasse.
     *
     * @param message Die Nachricht der Erinnerung.
     * @param reminderTime Die Zeit, zu der die Erinnerung angezeigt werden soll.
     */
    public Reminder(String message, LocalDateTime reminderTime) {
        this.message = message;
        this.reminderTime = reminderTime;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }
}
