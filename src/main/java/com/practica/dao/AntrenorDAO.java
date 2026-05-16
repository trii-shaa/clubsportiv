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
        String sql = "INSERT INTO antrenori (nume, prenume, sectie) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, antrenor.getIdAntrenor());
            stmt.setString(2, antrenor.getNumeAntrenor());
            stmt.setString(3, antrenor.getPrenumeAntrenor());
            stmt.setString(4, antrenor.getEmailAntrenor());
            stmt.setInt(5, antrenor.getTelefonAntrenor());
            stmt.setInt(6, antrenor.getIdSectie());
            stmt.setDouble(7, antrenor.getSalariu());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Antrenor> getAll() throws SQLException {
        List<Antrenor> antrenori = new ArrayList<>();
        String sql = "SELECT * FROM antrenori";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Antrenor antrenor = new Antrenor(
                    rs.getInt("idAntrenor"),
                    rs.getString("nume"),
                    rs.getString("prenume"),
                    rs.getString("email"),
                    rs.getInt("telefon"),
                    rs.getInt("idSectie"),
                    rs.getDouble("salariu")
                );
                antrenori.add(antrenor);
            }
        }
        return antrenori;
    }

    @Override
    public void update(Antrenor antrenor) throws SQLException {
        String sql = "UPDATE antrenori SET nume = ?, prenume = ?, sectie = ? WHERE idAntrenor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
             stmt.setInt(1, antrenor.getIdAntrenor());
            stmt.setString(2, antrenor.getNumeAntrenor());
            stmt.setString(3, antrenor.getPrenumeAntrenor());
            stmt.setString(4, antrenor.getEmailAntrenor());
            stmt.setInt(5, antrenor.getTelefonAntrenor());
            stmt.setInt(6, antrenor.getIdSectie());
            stmt.setDouble(7, antrenor.getSalariu());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM antrenori WHERE idAntrenor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }       

    public Antrenor getById(int id) throws SQLException {
        String sql = "SELECT * FROM antrenori WHERE idAntrenor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Antrenor(
                        rs.getInt("idAntrenor"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("email"),
                        rs.getInt("telefon"),
                        rs.getInt("idSectie"),
                        rs.getDouble("salariu")
                    );
                }
            }
        }
        return null; 
    }
    
}
