package com.practica.enums;

public enum TipSectie {
    CARDIO("Cardio"), 
    YOGA("Yoga"), 
    PILATES("Pilates"), 
    BOX("Box"), 
    INOT("Înot"), 
    ANTRENOR_PERSONAL("Antrenor Personal"), 
    FITNESS("Fitness");

    private final String numeAfisare;

    TipSectie(String numeAfisare) {
        this.numeAfisare = numeAfisare;
    }

    public String getNumeAfisare() {
        return this.numeAfisare;
    }
}