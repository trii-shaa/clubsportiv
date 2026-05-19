package com.practica.dao;

import com.practica.model.Abonament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbonamentDAO extends BaseDAO<Abonament> {

    public AbonamentDAO() throws SQLException {
        super();
    }

    private Abonament fromResultSet(ResultSet rs) throws SQLException {
        return new Abonament(
            rs.getInt("ID_Abonament"),
            rs.getString("Denumire"),
            rs.getString("Descriere"),
            rs.getDate("Data_inceput").toLocalDate(),
            rs.getDate("Data_expirarii").toLocalDate(),
            rs.getDouble("Pret")
        );
    }

    @Override
    public void add(Abonament a) throws SQLException {
        String sql = "INSERT INTO Abonamente VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, a.getIdAbonament());
        ps.setString(2, a.getDenumire());
        ps.setString(3, a.getDescriere());
        ps.setDate(4, Date.valueOf(a.getDataInceput()));
        ps.setDate(5, Date.valueOf(a.getDataExpirarii()));
        ps.setDouble(6, a.getPret());
        ps.executeUpdate();
    }

    @Override
    public List<Abonament> getAll() throws SQLException {
        List<Abonament> lista = new ArrayList<>();
        ResultSet rs = connection.createStatement()
            .executeQuery("SELECT * FROM Abonamente");
        while (rs.next()) lista.add(fromResultSet(rs));
        return lista;
    }

    public Abonament getById(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "SELECT * FROM Abonamente WHERE ID_Abonament=?"
        );
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return fromResultSet(rs);
        return null;
    }

    @Override
    public void update(Abonament a) throws SQLException {
        String sql = "UPDATE Abonamente SET Denumire=?, Descriere=?, " +
                     "Data_inceput=?, Data_expirarii=?, Pret=? WHERE ID_Abonament=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, a.getDenumire());
        ps.setString(2, a.getDescriere());
        ps.setDate(3, Date.valueOf(a.getDataInceput()));
        ps.setDate(4, Date.valueOf(a.getDataExpirarii()));
        ps.setDouble(5, a.getPret());
        ps.setInt(6, a.getIdAbonament());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "DELETE FROM Abonamente WHERE ID_Abonament=?"
        );
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public List<Abonament> cautaDupaDenumire(String denumire) throws SQLException {
        List<Abonament> lista = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(
            "SELECT * FROM Abonamente WHERE Denumire LIKE ?"
        );
        ps.setString(1, "%" + denumire + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) lista.add(fromResultSet(rs));
        return lista;
    }
}