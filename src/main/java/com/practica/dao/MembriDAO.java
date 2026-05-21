package com.practica.dao;

import java.sql.*;
import java.util.*;

import com.practica.model.Membru;

public class MembriDAO extends BaseDAO<Membru> {

  public MembriDAO() throws SQLException { super(); }

    @Override
    public void add(Membru m) throws SQLException {
     String sql = "INSERT INTO Membri (nume, prenume, varsta, email, telefon, idSectie, idAntrenor, idAbonament) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
     PreparedStatement stmt = connection.prepareStatement(sql);
     stmt.setString(1, m.getNume());
     stmt.setString(2, m.getPrenume());
     stmt.setInt(3, m.getVarsta());
     stmt.setString(4, m.getEmail());
     stmt.setString(5, m.getTelefon());  
      stmt.setInt(6, m.getIdSectie());
      stmt.setInt(7, m.getIdAntrenor());
      stmt.setInt(8, m.getIdAbonament());
      stmt.executeUpdate();

}

    @Override
    public void update(Membru m) throws SQLException {
        String sql = "UPDATE Membri SET Nume = ?, Prenume = ?, Varsta = ?, Email = ?, Telefon = ?, ID_Sectie = ?, ID_Antrenor = ?, ID_Abonament = ? WHERE ID_Membru = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, m.getNume());
        stmt.setString(2, m.getPrenume());
        stmt.setInt(3, m.getVarsta());
        stmt.setString(4, m.getEmail());
        stmt.setString(5, m.getTelefon());
        stmt.setInt(6, m.getIdSectie());
        stmt.setInt(7, m.getIdAntrenor());
        stmt.setInt(8, m.getIdAbonament());
        stmt.setInt(9, m.getIdMembru());
        stmt.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Membri WHERE ID_Membru = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    @Override 
    public List<Membru> getAll() throws SQLException {
        List<Membru> lista = new ArrayList<>();
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Membri");
        while(rs.next()){
            lista.add(new Membru(
                rs.getInt("ID_Membru"),
                rs.getString("Nume"),
                rs.getString("Prenume"),
                rs.getInt("Varsta"),
                rs.getString("Email"),
                rs.getString("Telefon"),
                rs.getInt("ID_Sectie"),
                rs.getObject("ID_Antrenor") != null ? rs.getInt("ID_Antrenor") : null,
                rs.getInt("ID_Abonament")
            ));
        }
        return lista;
    }

    public List<Membru> cautaDupaNume(String nume) throws SQLException {
        List<Membru> lista = new ArrayList<>();
        String sql = "SELECT * FROM Membri WHERE Nume LIKE ?"; // Corectat numele tabelului din Membri în membru
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nume + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Membru(
                        rs.getInt("ID_Membru"),
                        rs.getString("Nume"),
                        rs.getString("Prenume"),
                        rs.getInt("Varsta"),
                        rs.getString("Email"),
                        rs.getString("Telefon"),
                        rs.getInt("ID_Sectie"),
                        rs.getObject("ID_Antrenor") != null ? rs.getInt("ID_Antrenor") : null,
                        rs.getInt("ID_Abonament")
                    ));
                }
            }
        }
        return lista;
    }

    public Membru cautaDupaId(int idMembru) throws SQLException {
    PreparedStatement ps = connection.prepareStatement(
        "SELECT * FROM Membri WHERE ID_Membru = ?"
    );
    ps.setInt(1, idMembru);
    ResultSet rs = ps.executeQuery();
    
    if (rs.next()) {
        return new Membru(
            rs.getInt("ID_Membru"),
            rs.getString("Nume"),
            rs.getString("Prenume"),
            rs.getInt("Varsta"),
            rs.getString("Email"),
            rs.getString("Telefon"),
            rs.getInt("ID_Sectie"),
            rs.getObject("ID_Antrenor") != null ? rs.getInt("ID_Antrenor") : null,
            rs.getInt("ID_Abonament")
        );
    }
    return null; 
}

public void updateDatePersonale(int idMembru, String email, String telefon) throws SQLException {
        String sql = "UPDATE Membri SET Email = ?, Telefon = ? WHERE ID_Membru = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, telefon);
            stmt.setInt(3, idMembru);
            stmt.executeUpdate();
        }
    }

    public List<Membru> filtreazaDupaSectie(int idSectie) throws SQLException {
    List<Membru> lista = new ArrayList<>();
    PreparedStatement ps = connection.prepareStatement(
        "SELECT * FROM Membri WHERE ID_Sectie=?"
    );
    ps.setInt(1, idSectie);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        lista.add(new Membru(
            rs.getInt("ID_Membru"),
            rs.getString("Nume"),
            rs.getString("Prenume"),
            rs.getInt("Varsta"),
            rs.getString("Email"),
            rs.getString("Telefon"),
            rs.getInt("ID_Sectie"),
            rs.getObject("ID_Antrenor") != null ? rs.getInt("ID_Antrenor") : null,
            rs.getInt("ID_Abonament")
        ));
    }
    return lista;
}
}