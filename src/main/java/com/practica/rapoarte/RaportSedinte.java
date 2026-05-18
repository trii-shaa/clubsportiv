package com.practica.rapoarte;

import com.practica.database.DatabaseConnection;
import com.practica.interfaces.Raportabil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RaportSedinte implements Raportabil {

    public static class RandRaport {
        private int idSedinta;
        private String dataSedintei;
        private String oraSedintei;
        private String numeMembru;
        private String numeAntrenor;

        public RandRaport(int idSedinta, String dataSedintei, String oraSedintei,
                          String numeMembru, String numeAntrenor) {
            this.idSedinta = idSedinta;
            this.dataSedintei = dataSedintei;
            this.oraSedintei = oraSedintei;
            this.numeMembru = numeMembru;
            this.numeAntrenor = numeAntrenor;
        }

        public int getIdSedinta() { return idSedinta; }
        public String getDataSedintei() { return dataSedintei; }
        public String getOraSedintei() { return oraSedintei; }
        public String getNumeMembru() { return numeMembru; }
        public String getNumeAntrenor() { return numeAntrenor; }

        @Override
        public String toString() {
            return idSedinta + "," + dataSedintei + "," + oraSedintei + ","
                 + numeMembru + "," + numeAntrenor;
        }
    }

    public List<RandRaport> getRaport() throws Exception {
        List<RandRaport> lista = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT s.ID_Sedinta, s.Data_Sedintei, s.Ora_Sedintei, " +
                     "m.Nume as Membru, a.Nume as Antrenor " +
                     "FROM Sedinte s " +
                     "JOIN Membri m ON s.ID_Membru = m.ID_Membru " +
                     "JOIN Antrenori a ON s.ID_Antrenor = a.ID_Antrenor " +
                     "WHERE s.Data_Sedintei >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                     "ORDER BY s.Data_Sedintei DESC";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()) {
            lista.add(new RandRaport(
                rs.getInt("ID_Sedinta"),
                rs.getString("Data_Sedintei"),
                rs.getString("Ora_Sedintei"),
                rs.getString("Membru"),
                rs.getString("Antrenor")
            ));
        }
        return lista;
    }

    @Override
    public String genereazaRaport() {
        try {
            List<RandRaport> lista = getRaport();
            StringBuilder sb = new StringBuilder();
            sb.append("RAPORT: Sedinte din ultima saptamana\n");
            sb.append("=".repeat(40)).append("\n");
            for (RandRaport r : lista) {
                sb.append("ID ").append(r.getIdSedinta())
                  .append(" | ").append(r.getDataSedintei())
                  .append(" ").append(r.getOraSedintei())
                  .append(" | Membru: ").append(r.getNumeMembru())
                  .append(" | Antrenor: ").append(r.getNumeAntrenor()).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "Eroare la generarea raportului: " + e.getMessage();
        }
    }
}