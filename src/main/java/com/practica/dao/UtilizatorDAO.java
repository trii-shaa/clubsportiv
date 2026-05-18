package com.practica.dao;

import com.practica.enums.Rol;
import com.practica.model.Utilizator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilizatorDAO extends BaseDAO<Utilizator> {

    public UtilizatorDAO() throws SQLException {
        super();
    }

    public Utilizator login(String username, String parola) throws SQLException {
        String sql = "SELECT * FROM Utilizatori WHERE Username = ? AND Parola = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, parola);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Utilizator(
                rs.getInt("ID_Utilizator"),
                rs.getString("Username"),
                rs.getString("Parola"),
                Rol.valueOf(rs.getString("Rol")),
                rs.getObject("ID_Membru") != null ? rs.getInt("ID_Membru") : null
            );
        }
        return null;
    }

    @Override
    public void add(Utilizator u) throws SQLException {
        String sql = "INSERT INTO Utilizatori (Username, Parola, Rol, ID_Membru) VALUES (?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, u.getUsername());
        ps.setString(2, u.getParola());
        ps.setString(3, u.getRol().name());
        if (u.getIdMembru() != null)
            ps.setInt(4, u.getIdMembru());
        else
            ps.setNull(4, Types.INTEGER);
        ps.executeUpdate();
    }

    @Override
    public List<Utilizator> getAll() throws SQLException {
        List<Utilizator> lista = new ArrayList<>();
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Utilizatori");
        while (rs.next()) {
            lista.add(new Utilizator(
                rs.getInt("ID_Utilizator"),
                rs.getString("Username"),
                rs.getString("Parola"),
                Rol.valueOf(rs.getString("Rol")),
                rs.getObject("ID_Membru") != null ? rs.getInt("ID_Membru") : null
            ));
        }
        return lista;
    }

    @Override
    public void update(Utilizator u) throws SQLException {
        String sql = "UPDATE Utilizatori SET Username=?, Parola=?, Rol=?, ID_Membru=? WHERE ID_Utilizator=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, u.getUsername());
        ps.setString(2, u.getParola());
        ps.setString(3, u.getRol().name());
        if (u.getIdMembru() != null)
            ps.setInt(4, u.getIdMembru());
        else
            ps.setNull(4, Types.INTEGER);
        ps.setInt(5, u.getIdUtilizator());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM Utilizatori WHERE ID_Utilizator=?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public boolean existaUsername(String username) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "SELECT COUNT(*) FROM Utilizatori WHERE Username=?"
        );
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }
}