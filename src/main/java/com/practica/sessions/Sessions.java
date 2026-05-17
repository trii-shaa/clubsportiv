package com.practica.sessions;

import com.practica.model.Utilizator;

public class Sessions {
    private static Utilizator utilizatorCurent;

    public static void setUtilizatorCurent(Utilizator u) {
        utilizatorCurent = u;
    }

    public static Utilizator getUtilizatorCurent() {
        return utilizatorCurent;
    }

    public static boolean esteAdmin() {
        return utilizatorCurent != null && utilizatorCurent.esteAdmin();
    }

    public static void logout() {
        utilizatorCurent = null;
    }
}