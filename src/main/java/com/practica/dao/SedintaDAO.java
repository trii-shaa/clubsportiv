package com.practica.dao;

import java.sql.*;
import java.util.*;

import com.practica.model.Sedinta;

public class SedintaDAO extends BaseDAO<Sedinta> {
    public SedintaDAO() throws SQLException {
        super();
    }

    @Override
    public void add(Sedinta s) throws SQLException {
        String sql = "INSERT INTO sedinta (dataSedinta, oraSedinta, idAntrenor, numeAntrenor, idMembru, numeMembru, idSectie) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(s.getData()));
            stmt.setTime(2, java.sql.Time.valueOf(s.getOra()));
            stmt.setInt(3, s.getIdAntrenor());
            stmt.setString(4, s.getNumeAntrenor());
            stmt.setInt(5, s.getIdMembru());
            stmt.setString(6, s.getNumeMembru());
            stmt.setInt(7, s.getIdSectie());
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Sedinta s) throws SQLException {
        String sql = "UPDATE sedinta SET dataSedinta = ?, oraSedinta = ?, idAntrenor = ?, numeAntrenor = ?, idMembru = ?, numeMembru = ?, idSectie = ? WHERE idSedinta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(s.getData()));
            stmt.setTime(2, java.sql.Time.valueOf(s.getOra()));
            stmt.setInt(3, s.getIdAntrenor());
            stmt.setString(4, s.getNumeAntrenor());
            stmt.setInt(5, s.getIdMembru());
            stmt.setString(6, s.getNumeMembru());
            stmt.setInt(7, s.getIdSectie());
            stmt.setInt(8, s.getIdSedinta()); 
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM sedinta WHERE idSedinta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Sedinta> getAll() throws SQLException {
        List<Sedinta> lista = new ArrayList<>();
        String sql = "SELECT * FROM sedinta";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()){
                lista.add(construiesteSedinta(rs));
            }
        }
        return lista;
    }

    public Sedinta getById(int id) throws SQLException {
        String sql = "SELECT * FROM sedinta WHERE idSedinta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next()){
                    return construiesteSedinta(rs);
                }
            }
        }
        return null;
    }

    public List<Sedinta> getByMembru(int idMembru) throws SQLException {
        List<Sedinta> lista = new ArrayList<>();
        String sql = "SELECT * FROM sedinta WHERE idMembru = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMembru);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(construiesteSedinta(rs));
                }
            }
        }
        return lista;
    }

    private Sedinta construiesteSedinta(ResultSet rs) throws SQLException {
        return new Sedinta(
            rs.getInt("idSedinta"), 
            rs.getDate("dataSedinta").toLocalDate(),
            rs.getTime("oraSedinta").toLocalTime(),
            rs.getInt("idAntrenor"),
            rs.getString("numeAntrenor"),
            rs.getInt("idMembru"),
            rs.getString("numeMembru"),
            rs.getInt("idSectie")
        );
    }
}