package com.practica.controller;

import com.practica.dao.UtilizatorDAO;
import com.practica.enums.Rol;
import com.practica.export.ExportService;
import com.practica.model.Utilizator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @FXML private TextField   txtCautare;
    @FXML private ComboBox<?> comboFiltruSectie;

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

            tableUtilizatori.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, nou) -> { if (nou != null) completeazaFormular(nou); });

        } catch (Exception e) {
            afiseazaEroare("Eroare la initializare: " + e.getMessage());
        }
    }

    private void incarcaDate() throws Exception {
        listaUtilizatori = FXCollections.observableArrayList(utilizatorDAO.getAll());
        tableUtilizatori.setItems(listaUtilizatori);
    }

    private void completeazaFormular(Utilizator u) {
        if (txtUsername != null) txtUsername.setText(u.getUsername());
        if (txtParola   != null) txtParola.setText(u.getParola());
        if (comboRol    != null) comboRol.setValue(u.getRol());
        if (txtIdMembru != null) txtIdMembru.setText(
            u.getIdMembru() != null ? String.valueOf(u.getIdMembru()) : "");
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

            Integer idMembru = parseIdMembru();
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
                golesterFormular();
                afiseazaSucces("Utilizatorul a fost sters!");
            } catch (Exception e) {
                afiseazaEroare("Eroare la stergere: " + e.getMessage());
            }
        }
    }

    @FXML
    public void handleModifica() {
        Utilizator selectat = tableUtilizatori.getSelectionModel().getSelectedItem();
        if (selectat == null) { afiseazaEroare("Selecteaza un utilizator!"); return; }
        try {
            String username = txtUsername.getText().trim();
            String parola   = txtParola.getText().trim();
            Rol    rol      = comboRol.getValue();

            if (username.isEmpty() || parola.isEmpty() || rol == null) {
                afiseazaEroare("Completeaza username, parola si rol!");
                return;
            }

            selectat.setUsername(username);
            selectat.setParola(parola);
            selectat.setRol(rol);
            selectat.setIdMembru(parseIdMembru());

            utilizatorDAO.update(selectat);
            incarcaDate();
            golesterFormular();
            afiseazaSucces("Utilizatorul a fost modificat!");
        } catch (Exception e) {
            afiseazaEroare("Eroare la modificare: " + e.getMessage());
        }
    }

    @FXML
    public void handleCautare() {
        if (txtCautare == null) return;
        String text = txtCautare.getText().trim().toLowerCase();
        if (text.isEmpty()) {
            tableUtilizatori.setItems(listaUtilizatori);
            return;
        }
        ObservableList<Utilizator> filtrat = FXCollections.observableArrayList(
            listaUtilizatori.stream()
                .filter(u -> u.getUsername().toLowerCase().contains(text) ||
                             (u.getIdMembru() != null && u.getIdMembru().toString().contains(text)))
                .collect(Collectors.toList())
        );
        tableUtilizatori.setItems(filtrat);
    }

    @FXML
    public void handleFiltruSectie() {
        tableUtilizatori.setItems(listaUtilizatori);
    }

    @FXML
    public void handleExport() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Exporta utilizatori");
            fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fc.showSaveDialog(tableUtilizatori.getScene().getWindow());
            if (file != null) {
                ExportService export = new ExportService();
                List<Utilizator> toti = utilizatorDAO.getAll();
                if (file.getName().endsWith(".csv"))
                    export.exportCSV(file.getAbsolutePath(), toti);
                else
                    export.exportTXT(file.getAbsolutePath(), toti);
                afiseazaSucces("Export realizat cu succes!");
            }
        } catch (Exception e) {
            afiseazaEroare("Eroare la export: " + e.getMessage());
        }
    }

    private Integer parseIdMembru() {
        if (txtIdMembru == null) return null;
        String text = txtIdMembru.getText().trim();
        if (text.isEmpty()) return null;
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            afiseazaEroare("ID Membru trebuie sa fie un numar!");
            return null;
        }
    }

    private void golesterFormular() {
        if (txtUsername != null) txtUsername.clear();
        if (txtParola   != null) txtParola.clear();
        if (comboRol    != null) comboRol.setValue(null);
        if (txtIdMembru != null) txtIdMembru.clear();
    }

    private void afiseazaEroare(String mesaj) {
        new Alert(Alert.AlertType.ERROR, mesaj, ButtonType.OK).showAndWait();
    }

    private void afiseazaSucces(String mesaj) {
        new Alert(Alert.AlertType.INFORMATION, mesaj, ButtonType.OK).showAndWait();
    }
}