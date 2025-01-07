package com.example.medicare_projekt;

import javafx.scene.Scene;
/**
 * Diese Klasse verwaltet die Szenenwechsel in der Anwendung.
 * SceneManager implementiert das Singleton-Muster, um sicherzustellen, dass nur eine einzige Instanz
 * dieser Klasse existiert. Sie ermöglicht das Speichern und Abrufen der vorherigen Szene, was für die
 * Navigation innerhalb der Anwendung nützlich ist (z.B. um nach dem Anzeigen eines Details zu einer
 * vorherigen Liste zurückzukehren).
 */
public class SceneManager {
    /**
     * Die einzige Instanz dieser Klasse (Singleton-Muster).
     */
    private static SceneManager instance;

    /**
     * Die vorherige Szene, von der aus zu einer anderen Szene gewechselt wurde.
     */
    private Scene previousScene;

    /**
     * Privater Konstruktor, um die Instanziierung von außen zu verhindern (Singleton-Muster).
     */
    private SceneManager() {}
    /**
     * Gibt die einzige Instanz des SceneManagers zurück (Singleton-Muster).
     * Wenn noch keine Instanz existiert, wird eine neue erstellt und zurückgegeben.
     *
     * @return Die Instanz des SceneManagers.
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }
    /**
     * Setzt die vorherige Szene.
     * Diese Methode wird aufgerufen, bevor zu einer neuen Szene gewechselt wird,
     * um die aktuelle Szene als "vorherige Szene" zu speichern.
     *
     * @param scene Die aktuelle Szene, die als "vorherige Szene" gespeichert werden soll.
     */
    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }
    /**
     * Gibt die vorherige Szene zurück.
     * Diese Methode kann verwendet werden, um zur vorherigen Szene zurückzukehren.
     *
     * @return Die vorherige Szene, oder null, wenn es keine vorherige Szene gibt.
     */
    public Scene getPreviousScene() {
        return previousScene;
    }
}

