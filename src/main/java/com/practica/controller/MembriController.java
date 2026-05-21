package com.practica.controller;

import com.practica.dao.MembriDAO;
import com.practica.export.ExportService;
import com.practica.model.Membru;
import com.practica.validation.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MembriController {

    @FXML private TableView<Membru> tableMembri;
    @FXML private TableColumn<Membru, Integer> colId;
    @FXML private TableColumn<Membru, String> colNume;
    @FXML private TableColumn<Membru, String> colPrenume;
    @FXML private TableColumn<Membru, Integer> colVarsta;
    @FXML private TableColumn<Membru, String> colEmail;
    @FXML private TableColumn<Membru, String> colTelefon;
    @FXML private TableColumn<Membru, Integer> colSectie;
    @FXML private TableColumn<Membru, Integer> colAbonament;

    @FXML private TextField txtId;
    @FXML private TextField txtNume;
    @FXML private TextField txtPrenume;
    @FXML private TextField txtVarsta;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefon;
    @FXML private TextField txtIdAntrenor;
    @FXML private ComboBox<String> comboSectie;
    @FXML private ComboBox<String> comboAbonament;
    @FXML private TextField txtCautare;
    @FXML private ComboBox<String> comboFiltruSectie;

    private final Map<String, Integer> mapSectii = new LinkedHashMap<>();
    private final Map<String, Integer> mapAbonamente = new LinkedHashMap<>();

    private MembriDAO membruDAO;
    private ObservableList<Membru> listaMembri;

    @FXML
    public void initialize() {
        try {
            membruDAO = new MembriDAO();

            colId.setCellValueFactory(new PropertyValueFactory<>("idMembru"));
            colNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
            colPrenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));
            colVarsta.setCellValueFactory(new PropertyValueFactory<>("varsta"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
            colSectie.setCellValueFactory(new PropertyValueFactory<>("idSectie"));
            colAbonament.setCellValueFactory(new PropertyValueFactory<>("idAbonament"));

            incarcaDropdownSectii();
            incarcaDropdownAbonamente();
            incarcaDate();

            tableMembri.getSelectionModel().selectedItemProperty().addListener((obs, old, nou) -> {
                if (nou != null) completeazaFormular(nou);
            });

        } catch (Exception e) {
            afiseazaEroare("Eroare la initializare: " + e.getMessage());
        }
    }

    private void incarcaDropdownSectii() {
        mapSectii.put("1 - Sectia Cardio", 1);
        mapSectii.put("2 - Sectia Yoga", 2);
        mapSectii.put("3 - Sectia Pilates", 3);
        mapSectii.put("4 - Sectia Box", 4);
        mapSectii.put("5 - Sectia Inot", 5);
        mapSectii.put("6 - Sectia Antrenori Personali", 6);
        mapSectii.put("7 - Sectia Fitness", 7);

        comboSectie.getItems().addAll(mapSectii.keySet());
        comboFiltruSectie.getItems().add("-- Toate sectiile --");
        comboFiltruSectie.getItems().addAll(mapSectii.keySet());
        comboFiltruSectie.setValue("-- Toate sectiile --");
    }

    private void incarcaDropdownAbonamente() {
        mapAbonamente.put("1 - Basic (450 MDL)", 1);
        mapAbonamente.put("2 - Full Pass (800 MDL)", 2);
        mapAbonamente.put("3 - Yoga & Zen (600 MDL)", 3);
        mapAbonamente.put("4 - Combat Mix (550 MDL)", 4);
        mapAbonamente.put("5 - Aqua Sport (1200 MDL)", 5);
        mapAbonamente.put("6 - Personal VIP (1500 MDL)", 6);
        mapAbonamente.put("7 - Student Fit (350 MDL)", 7);
        mapAbonamente.put("8 - Family Pack (1800 MDL)", 8);
        mapAbonamente.put("9 - Weekend Only (300 MDL)", 9);
        mapAbonamente.put("10 - Annual Pro (6500 MDL)", 10);

        comboAbonament.getItems().addAll(mapAbonamente.keySet());
    }

    private void incarcaDate() throws Exception {
        listaMembri = FXCollections.observableArrayList(membruDAO.getAll());
        tableMembri.setItems(listaMembri);
    }

    private void completeazaFormular(Membru m) {
        txtId.setText(String.valueOf(m.getIdMembru()));
        txtNume.setText(m.getNume());
        txtPrenume.setText(m.getPrenume());
        txtVarsta.setText(String.valueOf(m.getVarsta()));
        txtEmail.setText(m.getEmail());
        txtTelefon.setText(m.getTelefon());
        txtIdAntrenor.setText(m.getIdAntrenor() != null ? String.valueOf(m.getIdAntrenor()) : "");

        for (Map.Entry<String, Integer> entry : mapSectii.entrySet()) {
            if (entry.getValue() == m.getIdSectie()) {
                comboSectie.setValue(entry.getKey());
                break;
            }
        }

        for (Map.Entry<String, Integer> entry : mapAbonamente.entrySet()) {
            if (entry.getValue() == m.getIdAbonament()) {
                comboAbonament.setValue(entry.getKey());
                break;
            }
        }
    }

    @FXML
    public void handleAdauga() {
        try {
            Membru m = citesteFormular();
            membruDAO.add(m);
            listaMembri.add(m);
            golesterFormular();
            afiseazaSucces("Membrul a fost adaugat!");
        } catch (IllegalArgumentException e) {
            afiseazaEroare(e.getMessage());
        } catch (Exception e) {
            afiseazaEroare("Eroare la adaugare: " + e.getMessage());
        }
    }

    @FXML
    public void handleModifica() {
        try {
            Membru m = citesteFormular();
            membruDAO.update(m);
            incarcaDate();
            golesterFormular();
            afiseazaSucces("Membrul a fost modificat!");
        } catch (IllegalArgumentException e) {
            afiseazaEroare(e.getMessage());
        } catch (Exception e) {
            afiseazaEroare("Eroare la modificare: " + e.getMessage());
        }
    }

    @FXML
    public void handleSterge() {
        Membru selectat = tableMembri.getSelectionModel().getSelectedItem();
        if (selectat == null) { afiseazaEroare("Selecteaza un membru!"); return; }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare stergere");
        alert.setHeaderText(null);
        alert.setContentText("Stergi membrul " + selectat.getNume() + " " + selectat.getPrenume() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                membruDAO.delete(selectat.getIdMembru());
                listaMembri.remove(selectat);
                golesterFormular();
                afiseazaSucces("Membrul a fost sters!");
            } catch (Exception e) {
                afiseazaEroare("Eroare la stergere: " + e.getMessage());
            }
        }
    }

    @FXML
    public void handleCautare() {
        try {
            String termen = txtCautare.getText().trim();
            if (termen.isEmpty()) { incarcaDate(); return; }
            List<Membru> rezultate = membruDAO.cautaDupaNume(termen);
            tableMembri.setItems(FXCollections.observableArrayList(rezultate));
        } catch (Exception e) {
            afiseazaEroare("Eroare la cautare: " + e.getMessage());
        }
    }

    @FXML
    public void handleFiltruSectie() {
        try {
            String selectat = comboFiltruSectie.getValue();
            if (selectat == null || selectat.equals("-- Toate sectiile --")) {
                incarcaDate();
                return;
            }
            int idSectie = mapSectii.get(selectat);
            List<Membru> rezultate = membruDAO.filtreazaDupaSectie(idSectie);
            tableMembri.setItems(FXCollections.observableArrayList(rezultate));
        } catch (Exception e) {
            afiseazaEroare("Eroare la filtrare: " + e.getMessage());
        }
    }

    @FXML
    public void handleExport() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Exporta membri");
            fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fc.showSaveDialog(tableMembri.getScene().getWindow());
            if (file != null) {
                ExportService export = new ExportService();
                List<Membru> toti = membruDAO.getAll();
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

    private Membru citesteFormular() {
        Validator.validareCampGol(txtId.getText(), "ID");
        Validator.validareNume(txtNume.getText());
        Validator.validareNume(txtPrenume.getText());
        Validator.validareEmail(txtEmail.getText());
        Validator.validareTelefon(txtTelefon.getText());
        if (comboSectie.getValue() == null)
            throw new IllegalArgumentException("Selecteaza o sectie!");
        if (comboAbonament.getValue() == null)
            throw new IllegalArgumentException("Selecteaza un abonament!");

        int varsta;
        try {
            varsta = Integer.parseInt(txtVarsta.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Varsta trebuie sa fie un numar!");
        }
        Validator.validareVarsta(varsta);

        Integer idAntrenor = null;
        if (!txtIdAntrenor.getText().trim().isEmpty()) {
            try {
                idAntrenor = Integer.parseInt(txtIdAntrenor.getText().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("ID Antrenor trebuie sa fie un numar!");
            }
        }

        int idSectie = mapSectii.get(comboSectie.getValue());
        int idAbonament = mapAbonamente.get(comboAbonament.getValue());

        return new Membru(
            Integer.parseInt(txtId.getText()),
            txtNume.getText(),
            txtPrenume.getText(),
            varsta,
            txtEmail.getText(),
            txtTelefon.getText(),
            idSectie,
            idAntrenor,
            idAbonament
        );
    }

    private void golesterFormular() {
        txtId.clear(); txtNume.clear(); txtPrenume.clear();
        txtVarsta.clear(); txtEmail.clear(); txtTelefon.clear();
        txtIdAntrenor.clear();
        comboSectie.setValue(null);
        comboAbonament.setValue(null);
        txtCautare.clear();
        comboFiltruSectie.setValue("-- Toate sectiile --");
    }

    private void afiseazaEroare(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare"); alert.setHeaderText(null);
        alert.setContentText(mesaj); alert.showAndWait();
    }

    private void afiseazaSucces(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes"); alert.setHeaderText(null);
        alert.setContentText(mesaj); alert.showAndWait();
    }
}