package com.practica.controller;

import com.practica.sessions.Sessions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Optional;

public class MainController {

    @FXML private Label lblWelcome;

    @FXML
    public void initialize() {
        if (lblWelcome != null && Sessions.getUtilizatorCurent() != null) {
            lblWelcome.setText("Bun venit, " + Sessions.getUtilizatorCurent().getUsername() + "!");
        }
    }

    // ── Navigation helpers ───────────────────────────────────────────────────

    @FXML public void deschideMembri()      { deschide("/fxml/admin_membri.fxml",      "Membri"); }
    @FXML public void deschideAntrenori()   { deschide("/fxml/antrenori.fxml",          "Antrenori"); }
    @FXML public void deschideSedinte()     { deschide("/fxml/sedinte.fxml",            "Sedinte"); }
    @FXML public void deschideAbonamente()  { deschide("/fxml/admin_abonamente.fxml",         "Abonamente"); }
    @FXML public void deschideRapoarte()    { deschide("/fxml/rapoarte.fxml",           "Rapoarte"); }
    @FXML public void deschideUtilizatori() { deschide("/fxml/admin_utilizatori.fxml",  "Utilizatori"); }

    private void deschide(String fxmlPath, String titlu) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm());
            stage.setTitle("Club Sportiv – " + titlu);
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
        new Alert(Alert.AlertType.ERROR,
            "Nu s-a putut deschide fereastra:\n" + cause.getClass().getName() + "\n" + cause.getMessage())
            .showAndWait();
        e.printStackTrace();
        }
    }

    @FXML
    public void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Esti sigur ca vrei sa te deloghezi?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Sessions.logout();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
                Stage stage = (Stage) lblWelcome.getScene().getWindow();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm());
                stage.setScene(scene);
                stage.setTitle("Club Sportiv – Login");
                stage.setMinWidth(400);
                stage.setMinHeight(400);
                stage.show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Eroare la logout: " + e.getMessage())
                    .showAndWait();
            }
        }
    }
}