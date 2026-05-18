package com.practica.dao;

import java.sql.*;
import java.util.*;

import com.practica.enums.TipAbonament;
import com.practica.model.Abonament;
public class AbonamentDAO extends BaseDAO<Abonament> {
    public AbonamentDAO() throws SQLException {
        super();
    }
    
    @Override 
    public void add(Abonament abonament) throws SQLException {
        String sql = "INSERT INTO abonamente (tipAbonament, pret) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, abonament.getIdAbonament());
            stmt.setString(2, abonament.getTipAbonament().name());
            stmt.setDouble(3, abonament.getPret());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Abonament abonament) throws SQLException {
        String sql = "UPDATE abonamente SET tipAbonament = ?, pret = ? WHERE idAbonament = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, abonament.getIdAbonament());
            stmt.setString(2, abonament.getTipAbonament().name());
            stmt.setDouble(3, abonament.getPret());
            stmt.executeUpdate(); 
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM abonamente WHERE idAbonament = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate(); 
        }
    }

    @Override
    public List<Abonament> getAll() throws SQLException {
        List<Abonament> abonamente = new ArrayList<>();
        String sql = "SELECT * FROM abonamente";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Abonament abonament = new Abonament( 
                    rs.getInt("idAbonament"),
                    TipAbonament.valueOf(rs.getString("tipAbonament")),
                    rs.getDouble("pret")
                );
                abonamente.add(abonament);
            }
        }
        return abonamente;
    }

    public Abonament getById(int id) throws SQLException {
        String sql = "SELECT * FROM abonamente WHERE idAbonament = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Abonament(
                        rs.getInt("idAbonament"),
                        TipAbonament.valueOf(rs.getString("tipAbonament")),
                        rs.getDouble("pret")
                    );
                }
            }
        }
        return null; 
    }
    

    
}
