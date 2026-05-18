package com.practica.model;

import com.practica.enums.TipSectie;
public class Sectie {
    private int idSectie;
    private TipSectie tipSectie;
    private String descriere;
    private int capacitateMaxima;

    public Sectie(int idSectie, TipSectie tipSectie, String descriere, int capacitateMaxima) {
        this.idSectie = idSectie;
        this.tipSectie = tipSectie;
        this.descriere = descriere;
        this.capacitateMaxima = capacitateMaxima;
    }

    //Getteri
    public int getIdSectie() {
        return idSectie;
    }
    public TipSectie getTipSectie() {
        return tipSectie;
    }
    public String getDescriere() {
        return descriere;
    }
    public int getCapacitateMaxima() {
        return capacitateMaxima;
    }
    public String getNumeSectie() {
        return this.tipSectie != null ? this.tipSectie.getNumeAfisare() : "Fără secție";
    }
    //Setteri
    public void setIdSectie(int idSectie) {
        this.idSectie = idSectie;
    }
    public void setTipSectie(TipSectie tipSectie) {
        this.tipSectie = tipSectie;
    }
    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
    public void setCapacitateMaxima(int capacitateMaxima) {
        this.capacitateMaxima = capacitateMaxima;
    }

    @Override
    public String toString() {
        return "Sectie{" +
                "idSectie=" + idSectie +
                ", tipSectie=" + tipSectie +
                ", descriere='" + descriere + '\'' +
                ", capacitateMaxima=" + capacitateMaxima +
                '}';
    }
}
