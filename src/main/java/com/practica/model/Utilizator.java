package com.practica.model;

import com.practica.enums.Rol;

public class Utilizator {
   private int idUtilizator;
   private String username;
   private String parola;
   private Rol rol;
   private Integer idMembru;


    public Utilizator(int idUtilizator, String username, String parola, Rol rol, Integer idMembru) {
        this.idUtilizator = idUtilizator;
        this.username = username;
        this.parola = parola;
        this.rol = rol;
        this.idMembru = idMembru;
    }

    //Getteri
    public int getIdUtilizator() {
        return idUtilizator;
    }
    public String getUsername() {
        return username;
    }
    public String getParola() {
        return parola;
    }
    public Rol getRol() {
        return rol;
    }
    public Integer getIdMembru() {
        return idMembru;
    }

    //Setteri
    public void setIdUtilizator(int idUtilizator) {
        this.idUtilizator = idUtilizator;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setParola(String parola) {
        this.parola = parola;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    public void setIdMembru(Integer idMembru) { 
        this.idMembru = idMembru;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "idUtilizator=" + idUtilizator +
                ", username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                ", rol=" + rol +
                ", idMembru=" + idMembru +
                '}';

    }

    public boolean esteAdmin() {
        if (rol == Rol.ADMIN) {
            return true;
        } else {
            return false;
        }
    }
}
