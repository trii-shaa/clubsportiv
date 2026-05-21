package com.practica.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static Connection connection;

    private static String url;
    private static String username;
    private static String password;

    static {
        try (InputStream in = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (in == null) {
                throw new RuntimeException(
                    "db.properties not found on classpath. " +
                    "Place it in src/main/resources/");
            }
            Properties props = new Properties();
            props.load(in);
            url      = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");

        } catch (IOException e) {
            throw new RuntimeException("Failed to load db.properties: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                throw new SQLException(
                    "Eroare la conectarea la baza de date: " + e.getMessage(), e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try { connection.close(); } catch (SQLException ignored) {}
            connection = null;
        }
    }
}