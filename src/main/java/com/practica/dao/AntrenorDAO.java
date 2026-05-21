package com.practica.dao;

import java.sql.*;
import java.util.*;
import com.practica.model.Antrenor;

public class AntrenorDAO extends BaseDAO<Antrenor> {
    public AntrenorDAO() throws SQLException {
        super();
    }

    @Override
    public void add(Antrenor antrenor) throws SQLException {
        // CORECTAT: Adăugate toate coloanele corespunzătoare celor 7 parametri
        String sql = "INSERT INTO Antrenori (ID_Antrenor, Nume, Prenume, Email, Telefon, ID_Sectie, Salariu) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, antrenor.getIdAntrenor());
            stmt.setString(2, antrenor.getNumeAntrenor());
            stmt.setString(3, antrenor.getPrenumeAntrenor());
            stmt.setString(4, antrenor.getEmailAntrenor());
            stmt.setString(5, antrenor.getTelefonAntrenor());
            stmt.setInt(6, antrenor.getIdSectie());
            stmt.setDouble(7, antrenor.getSalariu());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Antrenor> getAll() throws SQLException {
        List<Antrenor> antrenori = new ArrayList<>();
        String sql = "SELECT * FROM Antrenori";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Antrenor antrenor = new Antrenor(
                    rs.getInt("ID_Antrenor"),
                    rs.getString("Nume"),
                    rs.getString("Prenume"),
                    rs.getString("Email"),
                    rs.getString("Telefon"),
                    rs.getInt("ID_Sectie"),
                    rs.getDouble("Salariu")
                );
                antrenori.add(antrenor);
            }
        }
        return antrenori;
    }

    @Override
    public void update(Antrenor antrenor) throws SQLException {
        // CORECTAT: S-a rescris query-ul corect pentru UPDATE
        String sql = "UPDATE Antrenori SET Nume = ?, Prenume = ?, Email = ?, Telefon = ?, ID_Sectie = ?, Salariu = ? WHERE ID_Antrenor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, antrenor.getNumeAntrenor());
            stmt.setString(2, antrenor.getPrenumeAntrenor());
            stmt.setString(3, antrenor.getEmailAntrenor());
            stmt.setString(4, antrenor.getTelefonAntrenor());
            stmt.setInt(5, antrenor.getIdSectie());
            stmt.setDouble(6, antrenor.getSalariu());
            stmt.setInt(7, antrenor.getIdAntrenor());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Antrenori WHERE ID_Antrenor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }       

    public Antrenor getById(int id) throws SQLException {
        String sql = "SELECT * FROM Antrenori WHERE ID_Antrenor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Antrenor(
                    rs.getInt("ID_Antrenor"),
                    rs.getString("Nume"),
                    rs.getString("Prenume"),
                    rs.getString("Email"),
                    rs.getString("Telefon"),
                    rs.getInt("ID_Sectie"),
                    rs.getDouble("Salariu")
                    );
                }
            }
        }
        return null; 
    }

    // CORECTAT: Metodă adăugată fiindcă este apelată în Controller
    public List<Antrenor> cautaDupaNume(String termen) throws SQLException {
        List<Antrenor> rezultate = new ArrayList<>();
        String sql = "SELECT * FROM Antrenori WHERE Nume LIKE ? OR Prenume LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + termen + "%");
            stmt.setString(2, "%" + termen + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rezultate.add(new Antrenor(
                    rs.getInt("ID_Antrenor"),
                    rs.getString("Nume"),
                    rs.getString("Prenume"),
                    rs.getString("Email"),
                    rs.getString("Telefon"),
                    rs.getInt("ID_Sectie"),
                    rs.getDouble("Salariu")
                    ));
                }
            }
        }
        return rezultate;
    }

   
    public List<Antrenor> filtreazaDupaSectie(int idSectie) throws SQLException {
        List<Antrenor> rezultate = new ArrayList<>();
        String sql = "SELECT * FROM Antrenori WHERE ID_Sectie = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSectie);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rezultate.add(new Antrenor(
                    rs.getInt("ID_Antrenor"),
                    rs.getString("Nume"),
                    rs.getString("Prenume"),
                    rs.getString("Email"),
                    rs.getString("Telefon"),
                    rs.getInt("ID_Sectie"),
                    rs.getDouble("Salariu")
                    ));
                }
            }
        }
        return rezultate;
    }
}