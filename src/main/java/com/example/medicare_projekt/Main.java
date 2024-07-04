package com.example.medicare_projekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInView.fxml"));
        AnchorPane root = loader.load();


        LogInController controller = loader.getController();
        controller.setStage(primaryStage);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller.setMainScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
