package com.practica.dao;

import java.sql.*;
import java.util.*;
import com.practica.enums.TipSectie;
import com.practica.model.Sectie;

public class SectieDAO extends BaseDAO<Sectie> {
    public SectieDAO() throws SQLException {
        super();
    }

    @Override
    public void add(Sectie s) throws SQLException {
        String sql = "INSERT INTO sectie (numeSectie, descriere, capacitateMaxima) VALUES (?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, s.getTipSectie().name());
            stmt.setString(2, s.getDescriere());
            stmt.setInt(3, s.getCapacitateMaxima());
            stmt.executeUpdate();
        }
    }

    @Override 
    public void update(Sectie s) throws SQLException {
        String sql = "UPDATE sectie SET numeSectie = ?, descriere = ?, capacitateMaxima = ? WHERE idSectie = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, s.getTipSectie().name());
            stmt.setString(2, s.getDescriere());
            stmt.setInt(3, s.getCapacitateMaxima());
            stmt.setInt(4, s.getIdSectie());
            stmt.executeUpdate();
        }
    }

    @Override 
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM sectie WHERE idSectie = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Sectie> getAll() throws SQLException {
        List<Sectie> lista = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM sectie")) {
            while(rs.next()){
                Sectie s = new Sectie(
                    rs.getInt("ID_Sectie"),
                    TipSectie.valueOf(rs.getString("NumeSectie")),
                    rs.getString("Descriere"),
                    rs.getInt("CapacitateMaxima")
                );
                lista.add(s);
            }
        }
        return lista;
    }

    public Sectie getById(int id) throws SQLException {
        String sql = "SELECT * FROM sectie WHERE idSectie = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next()){
                    return new Sectie(
                        rs.getInt("ID_Sectie"),
                        TipSectie.valueOf(rs.getString("NumeSectie")),
                        rs.getString("Descriere"),
                        rs.getInt("CapacitateMaxima")
                    );
                }
            }
        }
        return null;
    }
   
   
    public List<Sectie> cautaDupaDenumire(String denumire) throws SQLException {
        List<Sectie> lista = new ArrayList<>();
        String sql = "SELECT * FROM sectie WHERE NumeSectie = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, denumire);
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){
                    Sectie s = new Sectie(
                        rs.getInt("ID_Sectie"),
                        TipSectie.valueOf(rs.getString("NumeSectie")),
                        rs.getString("Descriere"),
                        rs.getInt("CapacitateMaxima")
                    );
                    lista.add(s);
                }
            }
        }
        return lista;
    }
}