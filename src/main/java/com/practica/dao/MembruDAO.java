package com.practica.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.practica.model.Membru;

public class MembruDAO extends BaseDAO<Membru> {

  public MembruDAO() throws SQLException { super(); }

    @Override
    public void add(Membru m) throws SQLException {
     String sql = "INSERT INTO membru (nume, prenume, varsta, email, telefon, idSectie, idAntrenor, idAbonament) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
     PreparedStatement stmt = connection.prepareStatement(sql);
     stmt.setString(1, m.getNumeMembru());
     stmt.setString(2, m.getPrenumeMembru());
     stmt.setInt(3, m.getVarsta());
     stmt.setString(4, m.getEmailMembru());
     stmt.setInt(5, m.getTelefonMembru());  
      stmt.setInt(6, m.getIdSectie());
      stmt.setInt(7, m.getIdAntrenor());
      stmt.setInt(8, m.getIdAbonament());
      stmt.executeUpdate();

}

    @Override
    public void update(Membru m) throws SQLException {
        String sql = "UPDATE membru SET nume = ?, prenume = ?, varsta = ?, email = ?, telefon = ?, idSectie = ?, idAntrenor = ?, idAbonament = ? WHERE idMembru = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, m.getNumeMembru());
        stmt.setString(2, m.getPrenumeMembru());
        stmt.setInt(3, m.getVarsta());
        stmt.setString(4, m.getEmailMembru());
        stmt.setInt(5, m.getTelefonMembru());
        stmt.setInt(6, m.getIdSectie());
        stmt.setInt(7, m.getIdAntrenor());
        stmt.setInt(8, m.getIdAbonament());
        stmt.setInt(9, m.getIdMembru());
        stmt.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM membru WHERE idMembru = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    @Override 
    public List<Membru> getAll() throws SQLException {
        List<Membru> lista = new ArrayList<>();
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM membru");
        while(rs.next()){
            lista.add(new Membru(
                rs.getInt("ID_Membru"),
                rs.getString("Nume"),
                rs.getString("Prenume"),
                rs.getInt("Varsta"),
                rs.getString("Email"),
                rs.getInt("Telefon"),
                rs.getInt("ID_Sectie"),
                rs.getObject("ID_Antrenor") != null ? rs.getInt("ID_Antrenor") : null,
                rs.getInt("ID_Abonament")
            ));
        }
        return lista;
    }

        public List<Membru> cautaDupaNume(String nume) throws SQLException {
        List<Membru> lista = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(
            "SELECT * FROM Membri WHERE Nume LIKE ?"
        );
        ps.setString(1, "%" + nume + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lista.add(new Membru(
                rs.getInt("ID_Membru"),
                rs.getString("Nume"),
                rs.getString("Prenume"),
                rs.getInt("Varsta"),
                rs.getString("Email"),
                rs.getInt("Telefon"),
                rs.getInt("ID_Sectie"),
                rs.getObject("ID_Antrenor") != null ? rs.getInt("ID_Antrenor") : null,
                rs.getInt("ID_Abonament")
            ));
        }
        return lista;
    }

}