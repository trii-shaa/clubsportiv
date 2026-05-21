package com.practica.controller;

import com.practica.dao.UtilizatorDAO;
import com.practica.model.Utilizator;
import com.practica.sessions.Sessions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField     txtUsername;
    @FXML private PasswordField txtParola;
    @FXML private Label         lblEroare;

    @FXML
    public void handleLogin(ActionEvent event) {
        // Un mesaj scurt în consolă ca să fim 100% siguri că butonul reacționează
        System.out.println("[DEBUG] Butonul Autentificare a fost apăsat.");

        String username = txtUsername.getText().trim();
        String parola   = txtParola.getText().trim();

        if (username.isEmpty() || parola.isEmpty()) {
            setEroare("Completează username-ul și parola!");
            return;
        }

        try {
            UtilizatorDAO dao = new UtilizatorDAO();
            Utilizator u = dao.login(username, parola);

            if (u == null) {
                setEroare("Username sau parolă incorectă!");
                return;
            }

            Sessions.setUtilizatorCurent(u);

            String fxml = u.esteAdmin() ? "/fxml/main.fxml" : "/fxml/user_dashboard.fxml";
            String titlu = u.esteAdmin() ? "Club Sportiv – Admin" : "Club Sportiv – Profilul meu";

            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle(titlu);
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            stage.show();

        } catch (Exception e) {
            // Printează eroarea completă în terminal (ex: dacă pică conexiunea la MySQL/baza de date)
            e.printStackTrace(); 
            setEroare("Eroare sistem: " + e.getMessage());
        }
    }

    private void setEroare(String mesaj) {
        if (lblEroare != null) {
            lblEroare.setText(mesaj);
        } else {
            // Fail-safe în caz că label-ul tot nu e mapat corect în FXML
            System.out.println("[Eroare Aplicație]: " + mesaj);
        }
    }
}