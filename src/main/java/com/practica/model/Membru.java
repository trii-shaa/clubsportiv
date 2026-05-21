package com.practica.model;

public class Membru {
    private int idMembru;
    private String nume;
    private String prenume;
    private int varsta;
    private String email;
    private String telefon;
    private int idSectie;
    private Integer idAntrenor;
    private int idAbonament;

    public Membru(int idMembru, String nume, String prenume, int varsta,
                  String email, String telefon, int idSectie,
                  Integer idAntrenor, int idAbonament) {
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

    public int getIdMembru() { return idMembru; }
    public String getNume() { return nume; }
    public String getPrenume() { return prenume; }
    public int getVarsta() { return varsta; }
    public String getEmail() { return email; }
    public String getTelefon() { return telefon; }
    public int getIdSectie() { return idSectie; }
    public Integer getIdAntrenor() { return idAntrenor; }
    public int getIdAbonament() { return idAbonament; }

    public void setNume(String nume) { this.nume = nume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }
    public void setVarsta(int varsta) { this.varsta = varsta; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public void setIdSectie(int idSectie) { this.idSectie = idSectie; }
    public void setIdAntrenor(Integer idAntrenor) { this.idAntrenor = idAntrenor; }
    public void setIdAbonament(int idAbonament) { this.idAbonament = idAbonament; }

    @Override
    public String toString() {
        return idMembru + "," + nume + "," + prenume + "," + varsta + ","
             + email + "," + telefon + "," + idSectie + ","
             + (idAntrenor != null ? idAntrenor : "N/A") + "," + idAbonament;
    }
}