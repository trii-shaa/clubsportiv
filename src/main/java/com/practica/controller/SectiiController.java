package com.practica.controller;

import com.practica.dao.SectieDAO;
import com.practica.enums.TipSectie;
import com.practica.export.ExportService;
import com.practica.model.Sectie;
import com.practica.validation.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class SectiiController {

    @FXML private TableView<Sectie> tableSectii;
    @FXML private TableColumn<Sectie, Integer> colId;
    @FXML private TableColumn<Sectie, String> colDenumire;
    @FXML private TableColumn<Sectie, String> colDescriere;
    @FXML private TableColumn<Sectie, Integer> colCapacitate;

    @FXML private ComboBox<TipSectie> comboTip;
    @FXML private TextField txtDescriere;
    @FXML private TextField txtCapacitate;

    private SectieDAO sectieDAO;
    private ObservableList<Sectie> listaSectii;

    @FXML
    public void initialize() {
        try {
            sectieDAO = new SectieDAO();

            colId.setCellValueFactory(new PropertyValueFactory<>("idSectie"));
            colDenumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
            colDescriere.setCellValueFactory(new PropertyValueFactory<>("descriere"));
            colCapacitate.setCellValueFactory(new PropertyValueFactory<>("capacitate"));

            comboTip.getItems().addAll(TipSectie.values());

            incarcaDate();

            tableSectii.getSelectionModel().selectedItemProperty().addListener((obs, old, nou) -> {
                if (nou != null) completeazaFormular(nou);
            });

        } catch (Exception e) {
            afiseazaEroare("Eroare la initializare: " + e.getMessage());
        }
    }

    private void incarcaDate() throws Exception {
        listaSectii = FXCollections.observableArrayList(sectieDAO.getAll());
        tableSectii.setItems(listaSectii);
    }

    private void completeazaFormular(Sectie s) {
        comboTip.setValue(s.getTipSectie());
        txtDescriere.setText(s.getDescriere());
        txtCapacitate.setText(String.valueOf(s.getCapacitateMaxima()));
    }

    @FXML
    public void handleModifica() {
        Sectie selectata = tableSectii.getSelectionModel().getSelectedItem();
        if (selectata == null) { afiseazaEroare("Selecteaza o sectie din tabel!"); return; }

        try {
            Validator.validareCampGol(txtDescriere.getText(), "Descriere");

            int capacitate;
            try {
                capacitate = Integer.parseInt(txtCapacitate.getText());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Capacitatea trebuie sa fie un numar intreg!");
            }
            if (capacitate <= 0)
                throw new IllegalArgumentException("Capacitatea trebuie sa fie mai mare ca 0!");

            selectata.setDescriere(txtDescriere.getText());
            selectata.setCapacitateMaxima(capacitate);

            sectieDAO.update(selectata);
            incarcaDate();
            golesterFormular();
            afiseazaSucces("Sectia a fost modificata!");

        } catch (IllegalArgumentException e) {
            afiseazaEroare(e.getMessage());
        } catch (Exception e) {
            afiseazaEroare("Eroare la modificare: " + e.getMessage());
        }
    }

    @FXML
    public void handleExport() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Exporta sectii");
            fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fc.showSaveDialog(tableSectii.getScene().getWindow());
            if (file != null) {
                ExportService export = new ExportService();
                List<Sectie> toate = sectieDAO.getAll();
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

    private void golesterFormular() {
        comboTip.setValue(null);
        txtDescriere.clear();
        txtCapacitate.clear();
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