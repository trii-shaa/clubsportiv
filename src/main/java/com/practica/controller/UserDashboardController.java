package com.practica.controller;

import com.practica.dao.AbonamentDAO;
import com.practica.dao.MembriDAO;
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
    @FXML private TableColumn<Sedinta, Integer> colId;
    @FXML private TableColumn<Sedinta, String>  colData;
    @FXML private TableColumn<Sedinta, String>  colOra;
    @FXML private TableColumn<Sedinta, Integer> colIdMembru;
    @FXML private TableColumn<Sedinta, Integer> colIdAntrenor;

    @FXML private TextField  txtNouIdSedinta;
    @FXML private DatePicker dpNouaData;
    @FXML private TextField  txtNouaOra;
    @FXML private TextField  txtIdMembru;
    @FXML private TextField  txtIdAntrenor;

    private Membru membruCurent;
    private MembriDAO membruDAO;
    private SedintaDAO sedintaDAO;

  @FXML
public void initialize() {
    try {
        membruDAO  = new MembriDAO();
        sedintaDAO = new SedintaDAO();

        // DEBUG — uită-te în consolă la aceste valori
        System.out.println("=== DEBUG LOGIN ===");
        System.out.println("Utilizator din sesiune: " + Sessions.getUtilizatorCurent());
        System.out.println("ID_Membru din sesiune: " + Sessions.getUtilizatorCurent().getIdMembru());

        Integer idMembru = Sessions.getUtilizatorCurent().getIdMembru();

        if (idMembru == null) {
            afiseazaEroare("Contul tău NU are ID_Membru setat în baza de date!\nContactează administratorul să îl completeze.");
            return;
        }

        System.out.println("Caut membrul cu ID: " + idMembru);
        membruCurent = membruDAO.cautaDupaId(idMembru);
        System.out.println("Membrul găsit: " + membruCurent);

        if (membruCurent == null) {
            afiseazaEroare("ID_Membru=" + idMembru + " nu există în tabela Membri!\nVerifică baza de date.");
            return;
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("idSedinta"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colOra.setCellValueFactory(new PropertyValueFactory<>("ora"));
        colIdAntrenor.setCellValueFactory(new PropertyValueFactory<>("idAntrenor"));

        afiseazaProfil();
        incarcaSedinte();

    } catch (Exception e) {
        e.printStackTrace(); // afișează eroarea COMPLETĂ în consolă
        afiseazaEroare("Eroare la initializare dashboard: " + e.getMessage());
    }
}
    private void incarcaSedinte() throws Exception {
        List<Sedinta> sedinte = sedintaDAO.getByMembru(membruCurent.getIdMembru());
        tableSedinte.setItems(FXCollections.observableArrayList(sedinte));
    }

    private String getNumeSectie(int id) {
        switch (id) {
            case 1: return "Sectia Cardio";
            case 2: return "Sectia Yoga";
            case 3: return "Sectia Pilates";
            case 4: return "Sectia Box";
            case 5: return "Sectia Inot";
            case 6: return "Sectia Antrenori Personali";
            case 7: return "Sectia Fitness";
            default: return "Sectie necunoscuta";
        }
    }

    private void afiseazaProfil() throws Exception {
        lblWelcome.setText("Bun venit, " + membruCurent.getNume() + "!");
        lblNume.setText(membruCurent.getNume());
        lblPrenume.setText(membruCurent.getPrenume());
        lblVarsta.setText(String.valueOf(membruCurent.getVarsta()));
        lblSectie.setText(getNumeSectie(membruCurent.getIdSectie()));

        AbonamentDAO abonamentDAO = new AbonamentDAO();
        Abonament abonament = abonamentDAO.getById(membruCurent.getIdAbonament());
        if (abonament != null) {
            lblAbonament.setText(abonament.getDenumire() + " (" + abonament.getPret() + " MDL)");
        } else {
            lblAbonament.setText("Fără abonament");
        }

        txtEmail.setText(membruCurent.getEmail());
        txtTelefon.setText(membruCurent.getTelefon());
    }

    @FXML
    public void handleSalveazaDatePersonale() {
        try {
            String email   = txtEmail.getText();
            String telefon = txtTelefon.getText();

            Validator.validareEmail(email);
            Validator.validareTelefon(telefon);

            membruDAO.updateDatePersonale(membruCurent.getIdMembru(), email, telefon);
            membruCurent.setEmail(email);
            membruCurent.setTelefon(telefon);

            afiseazaSucces("Datele au fost salvate!");

        } catch (IllegalArgumentException e) {
            afiseazaEroare(e.getMessage());
        } catch (Exception e) {
            afiseazaEroare("Eroare la salvare: " + e.getMessage());
        }
    }

    @FXML
    public void handleAdaugaSedinta() {
        try {
            Validator.validareCampGol(txtNouIdSedinta.getText(), "ID Ședință");
            if (dpNouaData.getValue() == null) {
                throw new IllegalArgumentException("Vă rugăm să selectați o dată!");
            }
            Validator.validareCampGol(txtNouaOra.getText(), "Ora");
            Validator.validareCampGol(txtIdAntrenor.getText(), "ID Antrenor");

            int idSedinta  = Integer.parseInt(txtNouIdSedinta.getText().trim());
            java.time.LocalDate data = dpNouaData.getValue();
            java.time.LocalTime ora  = java.time.LocalTime.parse(txtNouaOra.getText().trim());
            int idAntrenor = Integer.parseInt(txtIdAntrenor.getText().trim());

            Sedinta nouaSedinta = new Sedinta(idSedinta, data, ora, membruCurent.getIdMembru(), idAntrenor);
            sedintaDAO.add(nouaSedinta);
            incarcaSedinte();

            txtNouIdSedinta.clear();
            dpNouaData.setValue(null);
            txtNouaOra.clear();
            txtIdMembru.clear();
            txtIdAntrenor.clear();

            afiseazaSucces("Ședința a fost programată cu succes!");

        } catch (java.time.format.DateTimeParseException e) {
            afiseazaEroare("Formatul orei este invalid! Folosiți HH:MM (ex: 15:30).");
        } catch (NumberFormatException e) {
            afiseazaEroare("ID-ul ședinței și ID-ul antrenorului trebuie să fie numere!");
        } catch (IllegalArgumentException e) {
            afiseazaEroare(e.getMessage());
        } catch (Exception e) {
            afiseazaEroare("Eroare la programare: " + e.getMessage());
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
        new Alert(Alert.AlertType.ERROR, mesaj, ButtonType.OK).showAndWait();
    }

    private void afiseazaSucces(String mesaj) {
        new Alert(Alert.AlertType.INFORMATION, mesaj, ButtonType.OK).showAndWait();
    }
}