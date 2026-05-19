package com.practica.validation;

import java.time.LocalDate;


public class Validator {

    public static void validareNume(String nume) throws IllegalArgumentException {
    // 1. Verificăm dacă este gol (păstrăm logica ta inițială)
    if (nume == null || nume.trim().isEmpty()) {
        throw new IllegalArgumentException("Numele nu poate fi gol!");
    }
    // ^[a-zA-ZăâîșțĂÂÎȘȚ\s-]+$ permite litere mari/mici (inclusiv diacritice)
    if (!nume.matches("^[a-zA-ZăâîșțĂÂÎȘȚ]+$")) {
        throw new IllegalArgumentException("Numele poate conține doar litere, spații sau cratime!");
    }
    }

     public static void validareEmail(String email) throws IllegalArgumentException {
        if (!email.matches("^[\\w.+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$"))
            throw new IllegalArgumentException("Email invalid!");
    }

        public static void validareVarsta(int varsta) throws IllegalArgumentException {
        if (varsta <= 0 || varsta > 120)
            throw new IllegalArgumentException("Varsta invalida!");
    }

     public static void validarePret(double pret) throws IllegalArgumentException {
        if (pret < 0)
            throw new IllegalArgumentException("Pretul nu poate fi negativ!");
    }

    public static void validareData(LocalDate data) throws IllegalArgumentException {
        if (data == null)
            throw new IllegalArgumentException("Data invalida!");
    }

    public static void validareTelefon(String telefon) throws IllegalArgumentException{
        if(telefon == null || telefon.trim().isEmpty() ){
            throw new IllegalArgumentException("Numarul de telefon nu poate fi gol!");
        }

        telefon = telefon.trim();

        if(!telefon.matches("^0[67]\\d{7}$")) {
            throw new IllegalArgumentException("Numarul de telefon trebuie sa se inceapă dupa exemplul: 06****** sau 07******!");
        }
    }

   public static void validareCampGol(String text, String numeCamp) throws IllegalArgumentException {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Câmpul '" + numeCamp + "' nu poate fi gol!");
        }
    }

    public static void validareDataExpirare(LocalDate dataInceput, LocalDate dataExpirare) throws IllegalArgumentException {
        if (dataInceput == null || dataExpirare == null) {
            throw new IllegalArgumentException("Ambele date trebuie să fie valide!");
        }
        if (dataExpirare.isBefore(dataInceput)) {
            throw new IllegalArgumentException("Data de expirare nu poate fi înainte de data de început!");
        }
    }

    public static void validareSalariu(double salariu) throws IllegalArgumentException {
        if (salariu <= 0) {
            throw new IllegalArgumentException("Salariul trebuie să fie mai mare decât 0!");
        }
    }
}