package com.practica.dao;

import com.practica.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T> {
    protected Connection connection;

    public BaseDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public abstract void add(T obj) throws SQLException;
    public abstract List<T> getAll() throws SQLException;
    public abstract void update(T obj) throws SQLException;
    public abstract void delete(int id) throws SQLException;
}