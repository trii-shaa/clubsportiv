package com.practica;

import com.practica.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConexiune {
    public static void main(String[] args) {
        System.out.println("Se încearcă conectarea la MariaDB...");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("=========================================");
                System.out.println("🎉 SUCCES! Te-ai conectat la MariaDB!");
                System.out.println("Baza de date este activă și răspunde.");
                System.out.println("=========================================");
            }
        } catch (SQLException e) {
            System.out.println("=========================================");
            System.out.println("❌ EROARE la conectare!");
            System.out.println("Mesaj eroare: " + e.getMessage());
            e.printStackTrace();
            System.out.println("=========================================");
        }
    }
}