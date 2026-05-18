package com.practica.controller;

import com.practica.export.ExportService;
import com.practica.rapoarte.RaportAntrenori;
import com.practica.rapoarte.RaportMembri;
import com.practica.rapoarte.RaportSedinte;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class RapoarteController {

    // --- Raport 1: Antrenori peste medie ---
    @FXML private TableView<RaportAntrenori.RandRaport> tableAntrenori;
    @FXML private TableColumn<RaportAntrenori.RandRaport, String> colA_Nume;
    @FXML private TableColumn<RaportAntrenori.RandRaport, String> colA_Prenume;
    @FXML private TableColumn<RaportAntrenori.RandRaport, Double> colA_Salariu;

    // --- Raport 2: Membri per sectie ---
    @FXML private TableView<RaportMembri.RandRaport> tableMembri;
    @FXML private TableColumn<RaportMembri.RandRaport, String> colM_Sectie;
    @FXML private TableColumn<RaportMembri.RandRaport, Integer> colM_NrMembri;

    // --- Raport 3: Sedinte ultima saptamana ---
    @FXML private TableView<RaportSedinte.RandRaport> tableSedinte;
    @FXML private TableColumn<RaportSedinte.RandRaport, Integer> colS_Id;
    @FXML private TableColumn<RaportSedinte.RandRaport, String> colS_Data;
    @FXML private TableColumn<RaportSedinte.RandRaport, String> colS_Ora;
    @FXML private TableColumn<RaportSedinte.RandRaport, String> colS_Membru;
    @FXML private TableColumn<RaportSedinte.RandRaport, String> colS_Antrenor;

    @FXML
    public void initialize() {
        // Configurare coloane raport 1
        colA_Nume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        colA_Prenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        colA_Salariu.setCellValueFactory(new PropertyValueFactory<>("salariu"));

        // Configurare coloane raport 2
        colM_Sectie.setCellValueFactory(new PropertyValueFactory<>("denumireSectie"));
        colM_NrMembri.setCellValueFactory(new PropertyValueFactory<>("nrMembri"));

        // Configurare coloane raport 3
        colS_Id.setCellValueFactory(new PropertyValueFactory<>("idSedinta"));
        colS_Data.setCellValueFactory(new PropertyValueFactory<>("dataSedintei"));
        colS_Ora.setCellValueFactory(new PropertyValueFactory<>("oraSedintei"));
        colS_Membru.setCellValueFactory(new PropertyValueFactory<>("numeMembru"));
        colS_Antrenor.setCellValueFactory(new PropertyValueFactory<>("numeAntrenor"));

        // Incarca toate rapoartele la deschiderea ferestrei
        incarcaRaport1();
        incarcaRaport2();
        incarcaRaport3();
    }

    // ---- Raport 1 ----

    @FXML
    public void incarcaRaport1() {
        try {
            List<RaportAntrenori.RandRaport> lista = new RaportAntrenori().getRaport();
            tableAntrenori.setItems(FXCollections.observableArrayList(lista));
        } catch (Exception e) {
            afiseazaEroare("Eroare raport 1: " + e.getMessage());
        }
    }

    @FXML
    public void exportRaport1() {
        try {
            List<RaportAntrenori.RandRaport> lista = new RaportAntrenori().getRaport();
            exportLista(lista);
        } catch (Exception e) {
            afiseazaEroare("Eroare export: " + e.getMessage());
        }
    }

    // ---- Raport 2 ----

    @FXML
    public void incarcaRaport2() {
        try {
            List<RaportMembri.RandRaport> lista = new RaportMembri().getRaport();
            tableMembri.setItems(FXCollections.observableArrayList(lista));
        } catch (Exception e) {
            afiseazaEroare("Eroare raport 2: " + e.getMessage());
        }
    }

    @FXML
    public void exportRaport2() {
        try {
            List<RaportMembri.RandRaport> lista = new RaportMembri().getRaport();
            exportLista(lista);
        } catch (Exception e) {
            afiseazaEroare("Eroare export: " + e.getMessage());
        }
    }

    // ---- Raport 3 ----

    @FXML
    public void incarcaRaport3() {
        try {
            List<RaportSedinte.RandRaport> lista = new RaportSedinte().getRaport();
            tableSedinte.setItems(FXCollections.observableArrayList(lista));
        } catch (Exception e) {
            afiseazaEroare("Eroare raport 3: " + e.getMessage());
        }
    }

    @FXML
    public void exportRaport3() {
        try {
            List<RaportSedinte.RandRaport> lista = new RaportSedinte().getRaport();
            exportLista(lista);
        } catch (Exception e) {
            afiseazaEroare("Eroare export: " + e.getMessage());
        }
    }

    // ---- Metoda comuna de export ----

    private void exportLista(List<?> lista) {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Salveaza raportul");
            fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fc.showSaveDialog(tableAntrenori.getScene().getWindow());
            if (file != null) {
                ExportService export = new ExportService();
                if (file.getName().endsWith(".csv"))
                    export.exportCSV(file.getAbsolutePath(), lista);
                else
                    export.exportTXT(file.getAbsolutePath(), lista);
                afiseazaSucces("Raportul a fost exportat cu succes!");
            }
        } catch (Exception e) {
            afiseazaEroare("Eroare la export: " + e.getMessage());
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