package com.practica.interfaces;

import java.io.IOException;
import java.util.List;

public interface Exportabil {
    void exportCVS(String filePath, List<?> date) throws IOException;
    void exportTXT(String filePath, List<?> date) throws IOException;
    void exportCSV(String filePath, List<?> date) throws IOException;
}
