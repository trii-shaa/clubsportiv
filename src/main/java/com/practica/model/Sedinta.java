package com.practica.model;

import java.time.*;

public class Sedinta {
    private int idSedinta;
    private LocalDate data;
    private LocalTime ora;
    private int idAntrenor;
    private String numeAntrenor;
    private int idMembru;
    private String numeMembru;
    private int idSectie;

    public Sedinta(int idSedinta, LocalDate data, LocalTime ora, int idAntrenor, String numeAntrenor, int idMembru, String numeMembru, int idSectie) {
        this.idSedinta = idSedinta;
        this.data = data;
        this.ora = ora;
        this.idAntrenor = idAntrenor;
        this.numeAntrenor = numeAntrenor;
        this.idMembru = idMembru;
        this.numeMembru = numeMembru;
        this.idSectie = idSectie;
    }

    //Getteri
    public int getIdSedinta() {
        return idSedinta;
    }
    public LocalDate getData() {
        return data;
    }
    public LocalTime getOra() {
        return ora;
    }
    public int getIdAntrenor() {
        return idAntrenor;
    }
    public String getNumeAntrenor() {
        return numeAntrenor;
    }
    public int getIdMembru() {
        return idMembru;
    }
    public String getNumeMembru() {
        return numeMembru;
    }
    public int getIdSectie() {
        return idSectie;
    }

    //Setteri
    public void setIdSedinta(int idSedinta) {
        this.idSedinta = idSedinta;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public void setOra(LocalTime ora) {
        this.ora = ora;
    }
    public void setIdAntrenor(int idAntrenor) {
        this.idAntrenor = idAntrenor;
    }
    public void setNumeAntrenor(String numeAntrenor) {
        this.numeAntrenor = numeAntrenor;
    }
    public void setIdMembru(int idMembru) {
        this.idMembru = idMembru;
    }
    public void setNumeMembru(String numeMembru) {
        this.numeMembru = numeMembru;
    }
    public void setIdSectie(int idSectie) {
        this.idSectie = idSectie;
    }

    @Override
    public String toString() {
        return "Sedinta{" +
                "idSedinta=" + idSedinta +
                ", data=" + data + 
                ", ora=" + ora +
                ", idAntrenor=" + idAntrenor +
                ", numeAntrenor='" + numeAntrenor + '\'' +
                ", idMembru=" + idMembru +
                ", numeMembru='" + numeMembru + '\'' +
                ", idSectie=" + idSectie +
                '}';
    }
}
