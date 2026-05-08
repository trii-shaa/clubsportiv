package com.practica.model;

import com.practica.enums.TipAbonament;
public class Abonament {
    private int idAbonament;
    private TipAbonament tipAbonament;
    private double pret;

    public Abonament(int idAbonament, TipAbonament tipAbonament, double pret) {
        this.idAbonament = idAbonament;
        this.tipAbonament = tipAbonament;
        this.pret = pret;
    }

    //Getteri
    public int getIdAbonament() {
        return idAbonament;
    }
    public TipAbonament getTipAbonament() {
        return tipAbonament;
    }
    public double getPret() {
        return pret;
    }

    //Setteri
    public void setIdAbonament(int idAbonament) {
        this.idAbonament = idAbonament; 
    }
    public void setTipAbonament(TipAbonament tipAbonament) {
        this.tipAbonament = tipAbonament; 
    }
    public void setPret(double pret) {
        this.pret = pret; 
    }

    @Override
    public String toString(){
        return "Abonament{" +
                "idAbonament=" + idAbonament +
                ", tipAbonament=" + tipAbonament +
                ", pret=" + pret +
                '}';
    }
}
