package com.practica;

import com.practica.database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(
            getClass().getResource("/fxml/login.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(
            getClass().getResource("/css/style.css").toExternalForm());

        primaryStage.setTitle("Club Sportiv – Autentificare");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(480);
        primaryStage.setMinHeight(420);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    @Override
    public void stop() {
        DatabaseConnection.closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}