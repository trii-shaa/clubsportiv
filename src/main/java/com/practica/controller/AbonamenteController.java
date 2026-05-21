package com.practica.controller;

import com.practica.dao.AbonamentDAO;
import com.practica.export.ExportService;
import com.practica.model.Abonament;
import com.practica.validation.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class AbonamenteController {

    @FXML private TableView<Abonament> tableAbonamente;
    @FXML private TableColumn<Abonament, Integer> colId;
    @FXML private TableColumn<Abonament, String> colDenumire;
    @FXML private TableColumn<Abonament, String> colDescriere;
    @FXML private TableColumn<Abonament, String> colDataInceput;
    @FXML private TableColumn<Abonament, String> colDataExpirarii;
    @FXML private TableColumn<Abonament, Double> colPret;

    @FXML private TextField txtId;
    @FXML private TextField txtDenumire;
    @FXML private TextField txtDescriere;
    @FXML private DatePicker dpDataInceput;
    @FXML private DatePicker dpDataExpirarii;
    @FXML private TextField txtPret;
    @FXML private TextField txtCautare;

    private AbonamentDAO abonamentDAO;
    private ObservableList<Abonament> listaAbonamente;

    @FXML
    public void initialize() {
        try {
            abonamentDAO = new AbonamentDAO();

            colId.setCellValueFactory(new PropertyValueFactory<>("idAbonament"));
            colDenumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
            colDataInceput.setCellValueFactory(new PropertyValueFactory<>("dataInceput"));
            colDataExpirarii.setCellValueFactory(new PropertyValueFactory<>("dataExpirarii"));
            colPret.setCellValueFactory(new PropertyValueFactory<>("pret"));

            incarcaDate();

            tableAbonamente.getSelectionModel().selectedItemProperty().addListener((obs, old, nou) -> {
                if (nou != null) completeazaFormular(nou);
            });

        } catch (Exception e) {
            afiseazaEroare("Eroare la initializare: " + e.getMessage());
        }
    }

    private void incarcaDate() throws Exception {
        listaAbonamente = FXCollections.observableArrayList(abonamentDAO.getAll());
        tableAbonamente.setItems(listaAbonamente);
    }

    private void completeazaFormular(Abonament a) {
        txtId.setText(String.valueOf(a.getIdAbonament()));
        txtDenumire.setText(a.getDenumire());
        dpDataInceput.setValue(a.getDataInceput());
        dpDataExpirarii.setValue(a.getDataExpirarii());
        txtPret.setText(String.valueOf(a.getPret()));
    }

    @FXML
    public void handleAdauga() {
        try {
            Abonament a = citesteFormular();
            abonamentDAO.add(a);
            listaAbonamente.add(a);
            golesterFormular();
            afiseazaSucces("Abonamentul a fost adaugat!");
        } catch (IllegalArgumentException e) {
            afiseazaEroare(e.getMessage());
        } catch (Exception e) {
            afiseazaEroare("Eroare la adaugare: " + e.getMessage());
        }
    }

    @FXML
    public void handleModifica() {
        try {
            Abonament a = citesteFormular();
            abonamentDAO.update(a);
            incarcaDate();
            golesterFormular();
            afiseazaSucces("Abonamentul a fost modificat!");
        } catch (IllegalArgumentException e) {
            afiseazaEroare(e.getMessage());
        } catch (Exception e) {
            afiseazaEroare("Eroare la modificare: " + e.getMessage());
        }
    }

    @FXML
    public void handleSterge() {
        Abonament selectat = tableAbonamente.getSelectionModel().getSelectedItem();
        if (selectat == null) { afiseazaEroare("Selecteaza un abonament!"); return; }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare stergere");
        alert.setHeaderText(null);
        alert.setContentText("Stergi abonamentul '" + selectat.getDenumire() + "'?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                abonamentDAO.delete(selectat.getIdAbonament());
                listaAbonamente.remove(selectat);
                golesterFormular();
                afiseazaSucces("Abonamentul a fost sters!");
            } catch (Exception e) {
                afiseazaEroare("Eroare la stergere: " + e.getMessage());
            }
        }
    }

    @FXML
    public void handleCautare() {
        try {
            String denumire= txtCautare.getText().trim();
            if (denumire.isEmpty()) {
                incarcaDate();
                return;
            }
            List<Abonament> rezultate = abonamentDAO.cautaDupaDenumire(denumire);
            tableAbonamente.setItems(FXCollections.observableArrayList(rezultate));
        } catch (Exception e) {
            afiseazaEroare("Eroare la cautare: " + e.getMessage());
        }
    }

    @FXML
    public void handleExport() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Exporta abonamente");
            fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fc.showSaveDialog(tableAbonamente.getScene().getWindow());
            if (file != null) {
                ExportService export = new ExportService();
                List<Abonament> toti = abonamentDAO.getAll();
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

    private Abonament citesteFormular() {
        Validator.validareCampGol(txtId.getText(), "ID");
        Validator.validareCampGol(txtDenumire.getText(), "Denumire");
        Validator.validareCampGol(txtDescriere.getText(), "Descriere");
        if (dpDataInceput.getValue() == null)
            throw new IllegalArgumentException("Selecteaza data de inceput!");
        if (dpDataExpirarii.getValue() == null)
            throw new IllegalArgumentException("Selecteaza data expirarii!");
        Validator.validareDataExpirare(dpDataInceput.getValue(), dpDataExpirarii.getValue());

        double pret;
        try {
            pret = Double.parseDouble(txtPret.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Pretul trebuie sa fie un numar!");
        }
        Validator.validarePret(pret);

        return new Abonament(
            Integer.parseInt(txtId.getText()),
            txtDenumire.getText(),
            txtDescriere.getText(),
            dpDataInceput.getValue(),
            dpDataExpirarii.getValue(),
            pret
        );
    }

    private void golesterFormular() {
        txtId.clear();
        txtDenumire.clear();
        txtDescriere.clear();
        dpDataInceput.setValue(null);
        dpDataExpirarii.setValue(null);
        txtPret.clear();
        txtCautare.clear();
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