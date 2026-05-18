package com.practica.rapoarte;

import com.practica.database.DatabaseConnection;
import com.practica.interfaces.Raportabil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RaportMembri implements Raportabil {

    public static class RandRaport {
        private String denumireSectie;
        private int nrMembri;

        public RandRaport(String denumireSectie, int nrMembri) {
            this.denumireSectie = denumireSectie;
            this.nrMembri = nrMembri;
        }

        public String getDenumireSectie() { return denumireSectie; }
        public int getNrMembri() { return nrMembri; }

        @Override
        public String toString() {
            return denumireSectie + "," + nrMembri;
        }
    }

    public List<RandRaport> getRaport() throws Exception {
        List<RandRaport> lista = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT s.Denumire, COUNT(m.ID_Membru) as NrMembri " +
                     "FROM Sectii s LEFT JOIN Membri m ON s.ID_Sectie = m.ID_Sectie " +
                     "GROUP BY s.Denumire ORDER BY NrMembri DESC";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()) {
            lista.add(new RandRaport(
                rs.getString("Denumire"),
                rs.getInt("NrMembri")
            ));
        }
        return lista;
    }

    @Override
    public String genereazaRaport() {
        try {
            List<RandRaport> lista = getRaport();
            StringBuilder sb = new StringBuilder();
            sb.append("RAPORT: Numar membri per sectie\n");
            sb.append("=".repeat(40)).append("\n");
            for (RandRaport r : lista) {
                sb.append(r.getDenumireSectie())
                  .append(" - ").append(r.getNrMembri()).append(" membri\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "Eroare la generarea raportului: " + e.getMessage();
        }
    }
}