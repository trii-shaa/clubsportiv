package com.practica.model;

public class Membru {
    private int idMembru;
    private String nume;
    private String prenume;
    private int varsta;
    private String email;
    private int telefon;
    private int idSectie;
    private Integer idAntrenor;
    private int idAbonament;

    public Membru(int idMembru, String nume, String prenume, int varsta, String email, int telefon, int idSectie, int idAntrenor, int idAbonament) {
        this.idMembru = idMembru;
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
        this.email = email;
        this.telefon = telefon;
        this.idSectie = idSectie;
        this.idAntrenor = idAntrenor;
        this.idAbonament = idAbonament;
    }

    //Getteri
    public int getIdMembru() {
        return idMembru;
    }
    public String getNumeMembru(){
        return nume;
    }
    public String getPrenumeMembru(){
        return prenume;
    }
    public int getVarsta() {
        return varsta;
    }
    public String getEmailMembru(){
        return email;
    }
    public int getTelefonMembru() {
        return telefon;
    }
    public int getIdSectie() {
        return idSectie;
    }
    public int getIdAntrenor() {
        return idAntrenor;
    }
    public int getIdAbonament() {
        return idAbonament;
    }

    //Setteri
    public void setIdMembru(int idMembru) {
        this.idMembru = idMembru; 
    }
    public void setNumeMembru(String nume){
        this.nume = nume;
    }
    public void setPrenumeMembru(String prenume){
        this.prenume = prenume;
    }
    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }
    public void setEmailMembru(String email){
        this.email = email;
    }
    public void setTelefonMembru(int telefon) {
        this.telefon = telefon;
    }
    public void setIdSectie(int idSectie) {
        this.idSectie = idSectie;
    }
    public void setIdAntrenor(int idAntrenor) {
        this.idAntrenor = idAntrenor;
    }
    public void setIdAbonament(int idAbonament) {
        this.idAbonament = idAbonament;
    }
    
    @Override 
    public String toString() {
        return "Membru{" +
                "idMembru=" + idMembru +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", varsta=" + varsta +
                ", email='" + email + '\'' +
                ", telefon=" + telefon +
                ", idSectie=" + idSectie +
                ", idAntrenor=" + idAntrenor +
                ", idAbonament=" + idAbonament +
                '}';
    }
}
