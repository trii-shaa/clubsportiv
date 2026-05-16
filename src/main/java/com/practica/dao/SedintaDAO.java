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
        String sql = "INSERT INTO sedinta (dataSedinta, oraSedinta, idSectie) VALUES (?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, s.getIdSedinta());
        stmt.setDate(2, java.sql.Date.valueOf(s.getData()));
        stmt.setTime(3, java.sql.Time.valueOf(s.getOra()));
        stmt.setInt(4, s.getIdAntrenor());
        stmt.setString(5, s.getNumeMembru());
        stmt.setInt(6, s.getIdMembru());
        stmt.setString(7, s.getNumeMembru());
        stmt.setInt(8, s.getIdSectie());
        stmt.executeUpdate();
    }

    @Override
    public void update(Sedinta s) throws SQLException {
        String sql = "UPDATE sedinta SET dataSedinta = ?, oraSedinta = ?, idSectie = ? WHERE idSedinta = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, s.getIdSedinta());
        stmt.setDate(2, java.sql.Date.valueOf(s.getData()));
        stmt.setTime(3, java.sql.Time.valueOf(s.getOra()));
        stmt.setInt(4, s.getIdAntrenor());
        stmt.setString(5, s.getNumeAntrenor());
        stmt.setInt(6, s.getIdMembru());
        stmt.setString(7, s.getNumeMembru());
        stmt.setInt(8, s.getIdSectie());
        stmt.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM sedinta WHERE idSedinta = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    @Override
    public List<Sedinta> getAll() throws SQLException {
        List<Sedinta> lista = new ArrayList<>();
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM sedinta");
        while(rs.next()){
            Sedinta s = new Sedinta(
                rs.getInt("ID_Sedinta"),
                rs.getDate("DataSedinta").toLocalDate(),
                rs.getTime("OraSedinta").toLocalTime(),
                rs.getInt("ID_Antrenor"),
                rs.getString("NumeAntrenor"),
                rs.getInt("ID_Membru"),
                rs.getString("NumeMembru"),
                rs.getInt("ID_Sectie")
            );
            lista.add(s);
        }
        return lista;
    }

    public Sedinta getById(int id) throws SQLException {
        String sql = "SELECT * FROM sedinta WHERE idSedinta = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            return new Sedinta(
                rs.getInt("ID_Sedinta"),
                rs.getDate("DataSedinta").toLocalDate(),
                rs.getTime("OraSedinta").toLocalTime(),
                rs.getInt("ID_Antrenor"),
                rs.getString("NumeAntrenor"),
                rs.getInt("ID_Membru"),
                rs.getString("NumeMembru"),
                rs.getInt("ID_Sectie")
            );
        }
        return null;
    }
}
