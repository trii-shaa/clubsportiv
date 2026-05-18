
package com.practica.export;

import com.practica.interfaces.Exportabil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportService implements Exportabil {

    @Override
    public void exportCSV(String filePath, List<?> date) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Object obj : date) {
                writer.write(obj.toString());
                writer.newLine();
            }
        }
    }

    @Override
    public void exportTXT(String filePath, List<?> date) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("=".repeat(60));
            writer.newLine();
            writer.write("EXPORT DATE - Club Sportiv");
            writer.newLine();
            writer.write("=".repeat(60));
            writer.newLine();
            int nr = 1;
            for (Object obj : date) {
                writer.write(nr + ". " + obj.toString());
                writer.newLine();
                nr++;
            }
            writer.write("=".repeat(60));
            writer.newLine();
            writer.write("Total inregistrari: " + date.size());
            writer.newLine();
        }
    }

    @Override
    public void exportCVS(String filePath, List<?> date) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exportCVS'");
    }
}