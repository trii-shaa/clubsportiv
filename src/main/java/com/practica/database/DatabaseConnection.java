package com.practica.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DatabaseConnection {

    private static Connection connection;

    private static String url = "jdbc:mysql://localhost:3306/ClubSportiv?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static String username = "root";
    private static String password = "1485";

   public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driverul MySQL nu a fost găsit pe classpath!", e);
            } catch (SQLException e) {
                throw new SQLException("Eroare la conectarea la baza de date: " + e.getMessage(), e);
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