package com.practica.controller;

import com.practica.dao.AbonamentDAO;
import com.practica.dao.MembruDAO;
import com.practica.dao.SedintaDAO;
import com.practica.export.ExportService;
import com.practica.model.Membru;
import com.practica.model.Sedinta;
import com.practica.model.Abonament;
import com.practica.sessions.Sessions;
import com.practica.validation.Validator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class UserDashboardController {

    @FXML private Label lblWelcome;
    @FXML private Label lblNume;
    @FXML private Label lblPrenume;
    @FXML private Label lblVarsta;
    @FXML private Label lblSectie;
    @FXML private Label lblAbonament;
    @FXML private Label lblAntrenor;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefon;

    @FXML private TableView<Sedinta> tableSedinte;
    @FXML private TableColumn<Sedinta, Integer> colIdSedinta;
    @FXML private TableColumn<Sedinta, String> colData;
    @FXML private TableColumn<Sedinta, String> colOra;
    @FXML private TableColumn<Sedinta, Integer> colAntrenor;

    private Membru membruCurent;
    private MembruDAO membruDAO;
    private SedintaDAO sedintaDAO;

    @FXML
    public void initialize() {
        try {
            membruDAO = new MembruDAO();
            sedintaDAO = new SedintaDAO();

            int idMembru = Sessions.getUtilizatorCurent().getIdMembru();
            membruCurent = membruDAO.cautaDupaId(idMembru);

            afiseazaProfil();
            incarcaSedinte();

        } catch (Exception e) {
            afiseazaEroare("Eroare la incarcarea datelor: " + e.getMessage());
        }
    }

    private void afiseazaProfil() throws Exception {
        lblWelcome.setText("Bun venit, " + membruCurent.getIdMembru() + " " + membruCurent.getNumeMembru() + "!");
        lblNume.setText(membruCurent.getNumeMembru());
        lblPrenume.setText(membruCurent.getPrenumeMembru());
        lblVarsta.setText(String.valueOf(membruCurent.getVarsta()));
        lblSectie.setText(membruCurent.getNumeSectie());

        int idAbonament = membruCurent.getIdAbonament();
        AbonamentDAO abonamentDAO = new AbonamentDAO();
        Abonament abonament = abonamentDAO.getById(idAbonament); 
        if (abonament != null) {
        lblAbonament.setText(abonament.getDenumireAbonament() + " (" + abonament.getPret() + " MDL)");
            }  else {
    lblAbonament.setText("Fără abonament");
            }

        txtEmail.setText(membruCurent.getEmailMembru());
        txtTelefon.setText(membruCurent.getTelefonMembru());
    }

    private void incarcaSedinte() throws Exception {
        colIdSedinta.setCellValueFactory(new PropertyValueFactory<>("idSedinta"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataSedintei"));
        colOra.setCellValueFactory(new PropertyValueFactory<>("oraSedintei"));
        colAntrenor.setCellValueFactory(new PropertyValueFactory<>("idAntrenor"));

        List<Sedinta> sedinte = sedintaDAO.getByMembru(membruCurent.getIdMembru());
        tableSedinte.setItems(FXCollections.observableArrayList(sedinte));
    }

    @FXML
    public void handleSalveazaDatePersonale() {
        try {
            String email = txtEmail.getText();
            String telefon = txtTelefon.getText();

            Validator.validareEmail(email);
            Validator.validareTelefon(telefon);

            membruDAO.updateDatePersonale(membruCurent.getIdMembru(), email, telefon);
            membruCurent.setEmailMembru(email);
            membruCurent.setTelefonMembru(telefon);

            afiseazaSucces("Datele au fost salvate!");

        } catch (IllegalArgumentException e) {
            afiseazaEroare(e.getMessage());
        } catch (Exception e) {
            afiseazaEroare("Eroare la salvare: " + e.getMessage());
        }
    }

    @FXML
    public void handleExportSedinte() {
        try {
            List<Sedinta> sedinte = sedintaDAO.getByMembru(membruCurent.getIdMembru());

            FileChooser fc = new FileChooser();
            fc.setTitle("Salveaza sedintele");
            fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fc.showSaveDialog(tableSedinte.getScene().getWindow());
            if (file != null) {
                ExportService export = new ExportService();
                if (file.getName().endsWith(".csv"))
                    export.exportCSV(file.getAbsolutePath(), sedinte);
                else
                    export.exportTXT(file.getAbsolutePath(), sedinte);
                afiseazaSucces("Export realizat cu succes!");
            }
        } catch (Exception e) {
            afiseazaEroare("Eroare la export: " + e.getMessage());
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
                stage.setScene(new Scene(root));
                stage.setTitle("Club Sportiv - Login");
                stage.show();
            } catch (Exception e) {
                afiseazaEroare("Eroare la logout: " + e.getMessage());
            }
        }
    }

    private void afiseazaEroare(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }

    private void afiseazaSucces(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }
}