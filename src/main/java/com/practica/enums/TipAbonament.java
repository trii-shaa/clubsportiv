package com.practica.enums;

public enum TipAbonament {
    BASIC("Basic Pass"), 
    FULL_PASS("Full Access Pass"), 
    YOGA_ZEN("Yoga & Zen"), 
    COMBAT_MIX("Combat Mix"), 
    AQUA_SPORT("Aqua Sport"), 
    PERSONAL_VIP("Personal Trainer VIP"), 
    STUDENT_FIX("Student Fix"), 
    FAMILY_PACK("Family Pack"), 
    WEEKEND_ONLY("Weekend Only"), 
    ANUAL_PRO("Anual Pro");

    private final String numeAfisare;

    TipAbonament(String numeAfisare) {
        this.numeAfisare = numeAfisare;
    }

    public String getNumeAfisare() {
        return this.numeAfisare;
    }
}