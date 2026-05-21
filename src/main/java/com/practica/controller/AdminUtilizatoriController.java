package com.practica.controller;

import com.practica.dao.UtilizatorDAO;
import com.practica.enums.Rol;
import com.practica.model.Utilizator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class AdminUtilizatoriController {

    @FXML private TableView<Utilizator>            tableUtilizatori;
    @FXML private TableColumn<Utilizator, Integer> colId;
    @FXML private TableColumn<Utilizator, String>  colUsername;
    @FXML private TableColumn<Utilizator, String>  colRol;
    @FXML private TableColumn<Utilizator, Integer> colIdMembru;

    @FXML private TextField     txtUsername;
    @FXML private PasswordField txtParola;
    @FXML private ComboBox<Rol> comboRol;
    @FXML private TextField     txtIdMembru;

    private UtilizatorDAO utilizatorDAO;
    private ObservableList<Utilizator> listaUtilizatori;

    @FXML
    public void initialize() {
        try {
            utilizatorDAO = new UtilizatorDAO();

            colId.setCellValueFactory(new PropertyValueFactory<>("idUtilizator"));
            colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
            colIdMembru.setCellValueFactory(new PropertyValueFactory<>("idMembru"));

            comboRol.getItems().addAll(Rol.values());

            incarcaDate();

        } catch (Exception e) {
            afiseazaEroare("Eroare la initializare: " + e.getMessage());
        }
    }

    private void incarcaDate() throws Exception {
        listaUtilizatori = FXCollections.observableArrayList(utilizatorDAO.getAll());
        tableUtilizatori.setItems(listaUtilizatori);
    }

    @FXML
    public void handleAdauga() {
        try {
            String username = txtUsername.getText().trim();
            String parola   = txtParola.getText().trim();
            Rol    rol      = comboRol.getValue();

            if (username.isEmpty() || parola.isEmpty() || rol == null) {
                afiseazaEroare("Completeaza username, parola si rol!");
                return;
            }
            if (utilizatorDAO.existaUsername(username)) {
                afiseazaEroare("Username-ul exista deja!");
                return;
            }

            Integer idMembru = null;
            if (!txtIdMembru.getText().trim().isEmpty()) {
                try { idMembru = Integer.parseInt(txtIdMembru.getText().trim()); }
                catch (NumberFormatException e) {
                    afiseazaEroare("ID Membru trebuie sa fie un numar!");
                    return;
                }
            }

            Utilizator u = new Utilizator(0, username, parola, rol, idMembru);
            utilizatorDAO.add(u);
            incarcaDate();
            golesterFormular();
            afiseazaSucces("Utilizatorul a fost adaugat!");

        } catch (Exception e) {
            afiseazaEroare("Eroare la adaugare: " + e.getMessage());
        }
    }

    @FXML
    public void handleSterge() {
        Utilizator selectat = tableUtilizatori.getSelectionModel().getSelectedItem();
        if (selectat == null) { afiseazaEroare("Selecteaza un utilizator!"); return; }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare stergere");
        alert.setHeaderText(null);
        alert.setContentText("Stergi utilizatorul '" + selectat.getUsername() + "'?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                utilizatorDAO.delete(selectat.getIdUtilizator());
                listaUtilizatori.remove(selectat);
                afiseazaSucces("Utilizatorul a fost sters!");
            } catch (Exception e) {
                afiseazaEroare("Eroare la stergere: " + e.getMessage());
            }
        }
    }

    private void golesterFormular() {
        txtUsername.clear();
        txtParola.clear();
        comboRol.setValue(null);
        txtIdMembru.clear();
    }

    private void afiseazaEroare(String mesaj) {
        new Alert(Alert.AlertType.ERROR, mesaj, ButtonType.OK).showAndWait();
    }

    private void afiseazaSucces(String mesaj) {
        new Alert(Alert.AlertType.INFORMATION, mesaj, ButtonType.OK).showAndWait();
    }
}