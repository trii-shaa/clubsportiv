package com.practica.controller;

import com.practica.dao.AntrenorDAO;
import com.practica.enums.TipSectie;
import com.practica.export.ExportService;
import com.practica.model.Antrenor;
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

public class AntrenoriController {

    @FXML private TableView<Antrenor> tableAntrenori;
    @FXML private TableColumn<Antrenor, Integer> colId;
    @FXML private TableColumn<Antrenor, String> colNume;
    @FXML private TableColumn<Antrenor, String> colPrenume;
    @FXML private TableColumn<Antrenor, String> colEmail;
    @FXML private TableColumn<Antrenor, String> colTelefon;
    @FXML private TableColumn<Antrenor, String> colSectie;
    @FXML private TableColumn<Antrenor, Double> colSalariu;

    @FXML private TextField txtId;
    @FXML private TextField txtNume;
    @FXML private TextField txtPrenume;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefon;
    @FXML private TextField txtSalariu;
    @FXML private ComboBox<TipSectie> comboSectie;
    @FXML private TextField txtCautare;
    @FXML private ComboBox<TipSectie> comboFiltruSectie;
    private AntrenorDAO antrenorDAO;
    private ObservableList<Antrenor> listaAntrenori;

    @FXML
    public void initialize() {
        try {

            this.antrenorDAO = new AntrenorDAO();

            colId.setCellValueFactory(new PropertyValueFactory<>("idAntrenor"));
            colNume.setCellValueFactory(new PropertyValueFactory<>("numeAntrenor"));
            colPrenume.setCellValueFactory(new PropertyValueFactory<>("prenumeAntrenor"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("emailAntrenor"));
            colTelefon.setCellValueFactory(new PropertyValueFactory<>("telefonAntrenor"));
            colSectie.setCellValueFactory(new PropertyValueFactory<>("idSectie"));
            colSalariu.setCellValueFactory(new PropertyValueFactory<>("salariu"));

            comboSectie.getItems().addAll(TipSectie.values());
            comboFiltruSectie.getItems().add(null);
            comboFiltruSectie.getItems().addAll(TipSectie.values());

            incarcaDate();

            tableAntrenori.getSelectionModel().selectedItemProperty().addListener((obs, old, nou) -> {
                if (nou != null) completeazaFormular(nou);
            });

        } catch (Exception e) {
            afiseazaEroare("Eroare la initializare: " + e.getMessage());
        }
    }

    private void incarcaDate() throws Exception {
        listaAntrenori = FXCollections.observableArrayList(antrenorDAO.getAll());
        tableAntrenori.setItems(listaAntrenori);
    }

    private void completeazaFormular(Antrenor a) {
        txtId.setText(String.valueOf(a.getIdAntrenor()));
        txtNume.setText(a.getNumeAntrenor());
        txtPrenume.setText(a.getPrenumeAntrenor());
        txtEmail.setText(a.getEmailAntrenor());
        txtTelefon.setText(a.getTelefonAntrenor());
        txtSalariu.setText(String.valueOf(a.getSalariu()));
        
        if (a.getIdSectie() >= 0 && a.getIdSectie() < TipSectie.values().length) {
            comboSectie.setValue(TipSectie.values()[a.getIdSectie()]);
        } else {
            comboSectie.setValue(null);
        }
    }

    @FXML
    public void handleAdauga() {
        try {
            Antrenor a = citesteFormular();
            antrenorDAO.add(a);
            listaAntrenori.add(a);
            golesterFormular();
            afiseazaSucces("Antrenorul a fost adaugat!");
        } catch (IllegalArgumentException e) {
            afiseazaEroare(e.getMessage());
        } catch (Exception e) {
            afiseazaEroare("Eroare la adaugare: " + e.getMessage());
        }
    }

    @FXML
    public void handleModifica() {
        try {
            Antrenor a = citesteFormular();
            antrenorDAO.update(a);
            incarcaDate();
            golesterFormular();
            afiseazaSucces("Antrenorul a fost modificat!");
        } catch (IllegalArgumentException e) {
            afiseazaEroare(e.getMessage());
        } catch (Exception e) {
            afiseazaEroare("Eroare la modificare: " + e.getMessage());
        }
    }

    @FXML
    public void handleSterge() {
        Antrenor selectat = tableAntrenori.getSelectionModel().getSelectedItem();
        if (selectat == null) { afiseazaEroare("Selecteaza un antrenor!"); return; }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare stergere");
        alert.setHeaderText(null);
        alert.setContentText("Stergi antrenorul " + selectat.getNumeAntrenor() + " " + selectat.getPrenumeAntrenor() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                antrenorDAO.delete(selectat.getIdAntrenor());
                listaAntrenori.remove(selectat);
                golesterFormular();
                afiseazaSucces("Antrenorul a fost sters!");
            } catch (Exception e) {
                afiseazaEroare("Eroare la stergere: " + e.getMessage());
            }
        }
    }

    @FXML
    public void handleCautare() {
        try {
            String termen = txtCautare.getText().trim();
            if (termen.isEmpty()) {
                incarcaDate();
                return;
            }
            List<Antrenor> rezultate = antrenorDAO.cautaDupaNume(termen);
            tableAntrenori.setItems(FXCollections.observableArrayList(rezultate));
        } catch (Exception e) {
            afiseazaEroare("Eroare la cautare: " + e.getMessage());
        }
    }

    @FXML
  public void handleFiltruSectie() {
        try {
            TipSectie sectie = comboFiltruSectie.getValue();
            if (sectie == null) {
                incarcaDate();
                return;
            }
            List<Antrenor> rezultate = antrenorDAO.filtreazaDupaSectie(sectie.ordinal());
            tableAntrenori.setItems(FXCollections.observableArrayList(rezultate));
        } catch (Exception e) {
            afiseazaEroare("Eroare la filtrare: " + e.getMessage());
        }
    }

    @FXML
    public void handleExport() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Exporta antrenori");
            fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fc.showSaveDialog(tableAntrenori.getScene().getWindow());
            if (file != null) {
                ExportService export = new ExportService();
                List<Antrenor> toti = antrenorDAO.getAll();
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

    private Antrenor citesteFormular() {
        Validator.validareCampGol(txtId.getText(), "ID");
        Validator.validareNume(txtNume.getText());
        Validator.validareNume(txtPrenume.getText());
        Validator.validareEmail(txtEmail.getText());
        Validator.validareTelefon(txtTelefon.getText());
        if (comboSectie.getValue() == null)
            throw new IllegalArgumentException("Selecteaza o sectie!");

        double salariu;
        try {
            salariu = Double.parseDouble(txtSalariu.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Salariul trebuie sa fie un numar!");
        }
        Validator.validareSalariu(salariu);
             return new Antrenor(
            Integer.parseInt(txtId.getText()),
            txtNume.getText(),
            txtPrenume.getText(),
            txtEmail.getText(),
            txtTelefon.getText(),
            comboSectie.getValue().ordinal(),
            salariu
        );
    }

    private void golesterFormular() {
        txtId.clear();
        txtNume.clear();
        txtPrenume.clear();
        txtEmail.clear();
        txtTelefon.clear();
        txtSalariu.clear();
        comboSectie.setValue(null);
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