package com.practica.model;

public class Antrenor {
    private int idAntrenor;
    private String numeAntrenor;
    private String prenumeAntrenor;
    private String emailAntrenor;
    private String telefonAntrenor;
    private int idSectie;
    private double salariu;
    
    public Antrenor(int idAntrenor, String numeAntrenor, String prenumeAntrenor, String emailAntrenor, String telefonAntrenor, int idSectie, double salariu) {
        this.idAntrenor = idAntrenor;
        this.numeAntrenor = numeAntrenor;
        this.prenumeAntrenor = prenumeAntrenor;
        this.emailAntrenor = emailAntrenor;
        this.telefonAntrenor = telefonAntrenor;
        this.idSectie = idSectie;
        this.salariu = salariu;
    }

    // Getteri
    public int getIdAntrenor() {
        return idAntrenor; 
    }
    public String getNumeAntrenor() {
        return numeAntrenor; 
    }
    public String getPrenumeAntrenor() {
        return prenumeAntrenor;
    }
    public String getEmailAntrenor() {
        return emailAntrenor; 
    }
    public String getTelefonAntrenor() {
        return telefonAntrenor;
    }   
    public int getIdSectie() { // CORECTAT: returnează int, nu TipSectie
        return idSectie; 
    }
    public double getSalariu() {
        return salariu;
    }

    // Setteri
    public void setIdAntrenor(int idAntrenor) {
        this.idAntrenor = idAntrenor;
    }
    public void setNumeAntrenor(String numeAntrenor) {
        this.numeAntrenor = numeAntrenor; 
    }
    public void setPrenumeAntrenor(String prenumeAntrenor) {
        this.prenumeAntrenor = prenumeAntrenor;
    }
    public void setEmailAntrenor(String emailAntrenor) {
        this.emailAntrenor = emailAntrenor; 
    }
    public void setTelefonAntrenor(String telefonAntrenor) {
        this.telefonAntrenor = telefonAntrenor;
    }
    public void setIdSectie(int idSectie) {
        this.idSectie = idSectie; 
    }
    public void setSalariu(double salariu) {
        this.salariu = salariu;
    }

    @Override
    public String toString() {
        return "Antrenor{" +
                "idAntrenor=" + idAntrenor +
                ", numeAntrenor='" + numeAntrenor + '\'' +
                ", prenumeAntrenor='" + prenumeAntrenor + '\'' +
                ", emailAntrenor='" + emailAntrenor + '\'' +
                ", telefonAntrenor='" + telefonAntrenor + '\'' +
                ", idSectie=" + idSectie +
                ", salariu=" + salariu +
                '}';
    }
}