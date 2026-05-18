package com.practica.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ClubSportiv", "root", "1485"
                );
            } catch (SQLException e) {
                throw new SQLException("Eroare la conectarea la baza de date: " + e.getMessage());
            }
        }
        return connection;
    }
}