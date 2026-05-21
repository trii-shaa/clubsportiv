package com.practica.model;

import java.time.*;

public class Sedinta {
    private int idSedinta;
    private LocalDate data;
    private LocalTime ora;
    private int idAntrenor;
    private int idMembru;

    public Sedinta(int idSedinta, LocalDate data, LocalTime ora, int idMembru, int idAntrenor) {
        this.idSedinta = idSedinta;
        this.data = data;
        this.ora = ora;
        this.idAntrenor = idAntrenor;
        this.idMembru = idMembru;
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
    public int getIdMembru() {
        return idMembru;
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
    public void setIdMembru(int idMembru) {
        this.idMembru = idMembru;
    }

    @Override
    public String toString() {
        return "Sedinta{" +
                "idSedinta=" + idSedinta +
                ", data=" + data + 
                ", ora=" + ora +
                ", idAntrenor=" + idAntrenor +
                ", idMembru=" + idMembru +
                '}';
    }
}
