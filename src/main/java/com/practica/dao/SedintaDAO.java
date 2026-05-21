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
    
    public List<Sedinta> filtreazaDupaData(String data) throws SQLException {
    List<Sedinta> lista = new ArrayList<>();
    PreparedStatement ps = connection.prepareStatement(
        "SELECT s.ID_Sedinta, s.Data_Sedintei, s.Ora_Sedintei, " +
        "s.ID_Antrenor, a.Nume as NumeAntrenor, " +
        "s.ID_Membru, m.Nume as NumeMembru, m.ID_Sectie " +
        "FROM Sedinte s " +
        "JOIN Antrenori a ON s.ID_Antrenor = a.ID_Antrenor " +
        "JOIN Membri m ON s.ID_Membru = m.ID_Membru " +
        "WHERE s.Data_Sedintei = ?"
    );
    ps.setString(1, data);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        lista.add(new Sedinta(
            rs.getInt("ID_Sedinta"),
            rs.getDate("Data_Sedintei").toLocalDate(),
            rs.getTime("Ora_Sedintei").toLocalTime(),
            rs.getInt("ID_Antrenor"),
            rs.getString("NumeAntrenor"),
            rs.getInt("ID_Membru"),
            rs.getString("NumeMembru"),
            rs.getInt("ID_Sectie")
        ));
    }
    return lista;
}
}