package com.practica.controller;

import com.practica.dao.UtilizatorDAO;
import com.practica.model.Utilizator;
import com.practica.sessions.Sessions;
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
    public void handleLogin() {
        String username = txtUsername.getText().trim();
        String parola   = txtParola.getText().trim();

        if (username.isEmpty() || parola.isEmpty()) {
            setEroare("Completeaza username-ul si parola!");
            return;
        }

        try {
            UtilizatorDAO dao = new UtilizatorDAO();
            Utilizator u = dao.login(username, parola);

            if (u == null) {
                setEroare("Username sau parola incorecta!");
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
            setEroare("Eroare: " + e.getMessage());
        }
    }

    private void setEroare(String mesaj) {
        if (lblEroare != null) lblEroare.setText(mesaj);
    }
}