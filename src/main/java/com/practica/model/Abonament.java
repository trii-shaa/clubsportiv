package com.practica.model;

import com.practica.enums.TipAbonament;

import java.time.LocalDate;

public class Abonament {
    private int idAbonament;
    private String denumire;
    private String descriere;
    private LocalDate dataInceput;
    private LocalDate dataExpirarii;
    private double pret;

    public Abonament(int idAbonament, String denumire, String descriere,
                     LocalDate dataInceput, LocalDate dataExpirarii, double pret) {
        this.idAbonament = idAbonament;
        this.denumire = denumire;
        this.descriere = descriere;
        this.dataInceput = dataInceput;
        this.dataExpirarii = dataExpirarii;
        this.pret = pret;
    }

    //getteri
    public int getIdAbonament() { 
        return idAbonament;
     }
    public String getDenumire() {
         return denumire; 
    }
    public String getDescriere() {
         return descriere; 
    }
    public LocalDate getDataInceput() {
         return dataInceput; 
    }
    public LocalDate getDataExpirarii() {
         return dataExpirarii; 
    }
    public double getPret() {
         return pret; 
    }

    //setteri
    public void setDenumire(String denumire) {
         this.denumire = denumire; 
    }
    public void setDescriere(String descriere) {
         this.descriere = descriere; 
    }
    public void setDataInceput(LocalDate dataInceput) {
         this.dataInceput = dataInceput; 
    }
    public void setDataExpirarii(LocalDate dataExpirarii) {
         this.dataExpirarii = dataExpirarii; 
    }
    public void setPret(double pret) {
         this.pret = pret; 
        }

    public TipAbonament getTipAbonament() {
        return TipAbonament.fromDenumire(denumire);
    }

    @Override
    public String toString() {
        return idAbonament + "," + denumire + "," + descriere + ","
             + dataInceput + "," + dataExpirarii + "," + pret;
    }
}