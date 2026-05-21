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
       
        String sql = "INSERT INTO Sedinte (ID_Sedinta, Data_Sedintei, Ora_Sedintei, ID_Membru, ID_Antrenor) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, s.getIdSedinta());
            stmt.setDate(2, java.sql.Date.valueOf(s.getData()));
            stmt.setTime(3, java.sql.Time.valueOf(s.getOra()));
            stmt.setInt(4, s.getIdMembru());
            stmt.setInt(5, s.getIdAntrenor());
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Sedinta s) throws SQLException {
        String sql = "UPDATE Sedinte SET Data_Sedintei = ?, Ora_Sedintei = ?, ID_Membru = ?, ID_Antrenor = ? WHERE ID_Sedinta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(5, s.getIdSedinta());
            stmt.setDate(1, java.sql.Date.valueOf(s.getData()));
            stmt.setTime(2, java.sql.Time.valueOf(s.getOra()));
            stmt.setInt(3, s.getIdMembru());
            stmt.setInt(4, s.getIdAntrenor());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Sedinte WHERE ID_Sedinta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Sedinta> getAll() throws SQLException {
        List<Sedinta> lista = new ArrayList<>();
        String sql = "SELECT * FROM Sedinte";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()){
                lista.add(construiesteSedinta(rs));
            }
        }
        return lista;
    }

    public Sedinta getById(int id) throws SQLException {
        String sql = "SELECT * FROM Sedinte WHERE ID_Sedinta = ?";
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
        String sql = "SELECT * FROM Sedinte WHERE ID_Membru = ?";
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
            rs.getInt("ID_Sedinta"), 
            rs.getDate("Data_Sedintei").toLocalDate(),
            rs.getTime("Ora_Sedintei").toLocalTime(),
            rs.getInt("ID_Membru"),
            rs.getInt("ID_Antrenor")
            
        );
    }
    
    public List<Sedinta> filtreazaDupaData(String data) throws SQLException {
        List<Sedinta> lista = new ArrayList<>();
      
        String sql = "SELECT s.ID_Sedinta, s.Data_Sedintei, s.Ora_Sedintei, s.ID_Antrenor, s.ID_Membru " +
                     "FROM Sedinte s " +
                     "JOIN Antrenori a ON s.ID_Antrenor = a.ID_Antrenor " +
                     "JOIN Membri m ON s.ID_Membru = m.ID_Membru " +
                     "WHERE s.Data_Sedintei = ?";
                     
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, data);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(construiesteSedinta(rs));
                }
            }
        }
        return lista;
    }
}