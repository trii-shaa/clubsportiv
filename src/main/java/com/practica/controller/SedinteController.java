package com.practica.controller;

import com.practica.dao.SedintaDAO;
import com.practica.export.ExportService;
import com.practica.model.Sedinta;
import com.practica.validation.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class SedinteController {

    @FXML private TableView<Sedinta> tableSedinte;
    
    @FXML private TableColumn<Sedinta, Integer> colId;
    @FXML private TableColumn<Sedinta, LocalDate> colData;
    @FXML private TableColumn<Sedinta, LocalTime> colOra;
    @FXML private TableColumn<Sedinta, Integer> colIdMembru;
    @FXML private TableColumn<Sedinta, Integer> colIdAntrenor;

    @FXML private TextField txtId;
    @FXML private DatePicker dpData;
    @FXML private TextField txtOra;
    @FXML private TextField txtIdMembru;
    @FXML private TextField txtIdAntrenor;
    @FXML private DatePicker dpFiltruData;

    private SedintaDAO sedintaDAO;
    private ObservableList<Sedinta> listaSedinte;

    @FXML
    public void initialize() {
        try {
            sedintaDAO = new SedintaDAO();

            colId.setCellValueFactory(new PropertyValueFactory<>("idSedinta"));
            colData.setCellValueFactory(new PropertyValueFactory<>("data"));
            colOra.setCellValueFactory(new PropertyValueFactory<>("ora"));
            colIdMembru.setCellValueFactory(new PropertyValueFactory<>("idMembru"));
            colIdAntrenor.setCellValueFactory(new PropertyValueFactory<>("idAntrenor"));
            incarcaDate();

            tableSedinte.getSelectionModel().selectedItemProperty().addListener((obs, old, nou) -> {
                if (nou != null) completeazaFormular(nou);
            });

        } catch (Exception e) {
            afiseazaEroare("Eroare la initializare: " + e.getMessage());
        }
    }

    private void incarcaDate() throws Exception {
        List<Sedinta> dateDinBaza = sedintaDAO.getAll();
    
    
    System.out.println(">>> DEBUG: Am gasit in baza de date " + dateDinBaza.size() + " sedinte.");
    for (Sedinta s : dateDinBaza) {
        System.out.println(s);
    }
        listaSedinte = FXCollections.observableArrayList(sedintaDAO.getAll());
        tableSedinte.setItems(listaSedinte);
    }

    private void completeazaFormular(Sedinta s) {
        txtId.setText(String.valueOf(s.getIdSedinta()));
        dpData.setValue(s.getData());
        txtOra.setText(s.getOra().toString());
        txtIdMembru.setText(String.valueOf(s.getIdMembru()));
        txtIdAntrenor.setText(String.valueOf(s.getIdAntrenor()));
    }

    @FXML
    public void handleAdauga() {
        try {
            Sedinta s = citesteFormular();
            sedintaDAO.add(s);
            listaSedinte.add(s);
            golesterFormular();
            afiseazaSucces("Sedinta a fost adaugata!");
        } catch (IllegalArgumentException e) {
            afiseazaEroare(e.getMessage());
        } catch (Exception e) {
            afiseazaEroare("Eroare la adaugare: " + e.getMessage());
        }
    }

    @FXML
    public void handleModifica() {
        try {
            Sedinta s = citesteFormular();
            sedintaDAO.update(s);
            incarcaDate();
            golesterFormular();
            afiseazaSucces("Sedinta a fost modificata!");
        } catch (IllegalArgumentException e) {
            afiseazaEroare(e.getMessage());
        } catch (Exception e) {
            afiseazaEroare("Eroare la modificare: " + e.getMessage());
        }
    }

    @FXML
    public void handleSterge() {
        Sedinta selectata = tableSedinte.getSelectionModel().getSelectedItem();
        if (selectata == null) { afiseazaEroare("Selecteaza o sedinta!"); return; }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare stergere");
        alert.setHeaderText(null);
        alert.setContentText("Stergi sedinta din " + selectata.getData() + " ora " + selectata.getOra() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                sedintaDAO.delete(selectata.getIdSedinta());
                listaSedinte.remove(selectata);
                golesterFormular();
                afiseazaSucces("Sedinta a fost stearsa!");
            } catch (Exception e) {
                afiseazaEroare("Eroare la stergere: " + e.getMessage());
            }
        }
    }

    @FXML
    public void handleFiltruData() {
        try {
            LocalDate data = dpFiltruData.getValue();
            if (data == null) {
                incarcaDate();
                return;
            }
            List<Sedinta> rezultate = sedintaDAO.filtreazaDupaData(data.toString());
            tableSedinte.setItems(FXCollections.observableArrayList(rezultate));
        } catch (Exception e) {
            afiseazaEroare("Eroare la filtrare: " + e.getMessage());
        }
    }

    @FXML
    public void handleResetFiltru() {
        dpFiltruData.setValue(null);
        try {
            incarcaDate();
        } catch (Exception e) {
            afiseazaEroare("Eroare la resetare: " + e.getMessage());
        }
    }

    @FXML
    public void handleExport() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Exporta sedinte");
            fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fc.showSaveDialog(tableSedinte.getScene().getWindow());
            if (file != null) {
                ExportService export = new ExportService();
                List<Sedinta> toate = sedintaDAO.getAll();
                if (file.getName().endsWith(".csv"))
                    export.exportCSV(file.getAbsolutePath(), toate);
                else
                    export.exportTXT(file.getAbsolutePath(), toate);
                afiseazaSucces("Export realizat cu succes!");
            }
        } catch (Exception e) {
            afiseazaEroare("Eroare la export: " + e.getMessage());
        }
    }

   private Sedinta citesteFormular() {
    Validator.validareCampGol(txtId.getText(), "ID");
    if (dpData.getValue() == null)
        throw new IllegalArgumentException("Selecteaza data sedintei!");
    Validator.validareCampGol(txtOra.getText(), "Ora");
    Validator.validareCampGol(txtIdMembru.getText(), "ID Membru");
    Validator.validareCampGol(txtIdAntrenor.getText(), "ID Antrenor");

    LocalTime ora;
    try {
        ora = LocalTime.parse(txtOra.getText());
    } catch (DateTimeParseException e) {
        throw new IllegalArgumentException("Ora trebuie in formatul HH:MM:SS (ex: 09:00:00)!");
    }

    int idMembru;
    int idAntrenor;
    try {
        idMembru = Integer.parseInt(txtIdMembru.getText());
        idAntrenor = Integer.parseInt(txtIdAntrenor.getText());
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("ID Membru si ID Antrenor trebuie sa fie numere!");
    }

    return new Sedinta(
        Integer.parseInt(txtId.getText()),
        dpData.getValue(),
        ora,
        idMembru,
        idAntrenor    
    );
}

    private void golesterFormular() {
        txtId.clear();
        dpData.setValue(null);
        txtOra.clear();
        txtIdMembru.clear();
        txtIdAntrenor.clear();
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