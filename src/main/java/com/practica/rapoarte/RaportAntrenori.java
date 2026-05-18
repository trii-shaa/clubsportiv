package com.practica.rapoarte;

import com.practica.database.DatabaseConnection;
import com.practica.interfaces.Raportabil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RaportAntrenori implements Raportabil {

    // Clasa care reprezinta un rand din raport
    public static class RandRaport {
        private String nume;
        private String prenume;
        private double salariu;

        public RandRaport(String nume, String prenume, double salariu) {
            this.nume = nume;
            this.prenume = prenume;
            this.salariu = salariu;
        }

        public String getNume() { return nume; }
        public String getPrenume() { return prenume; }
        public double getSalariu() { return salariu; }

        @Override
        public String toString() {
            return nume + "," + prenume + "," + salariu;
        }
    }

    public List<RandRaport> getRaport() throws Exception {
        List<RandRaport> lista = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT Nume, Prenume, Salariu FROM Antrenori " +
                     "WHERE Salariu > (SELECT AVG(Salariu) FROM Antrenori) " +
                     "ORDER BY Salariu DESC";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()) {
            lista.add(new RandRaport(
                rs.getString("Nume"),
                rs.getString("Prenume"),
                rs.getDouble("Salariu")
            ));
        }
        return lista;
    }

    @Override
    public String genereazaRaport() {
        try {
            List<RandRaport> lista = getRaport();
            StringBuilder sb = new StringBuilder();
            sb.append("RAPORT: Antrenori cu salariul peste medie\n");
            sb.append("=".repeat(40)).append("\n");
            for (RandRaport r : lista) {
                sb.append(r.getNume()).append(" ").append(r.getPrenume())
                  .append(" - ").append(r.getSalariu()).append(" MDL\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "Eroare la generarea raportului: " + e.getMessage();
        }
    }
}