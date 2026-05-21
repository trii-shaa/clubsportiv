package com.practica.enums;

public enum TipSectie {
    CARDIO(1, "Sectia Cardio"),
    YOGA(2, "Sectia Yoga"),
    PILATES(3, "Sectia Pilates"),
    BOX(4, "Sectia Box"),
    INOT(5, "Sectia Inot"),
    ANTRENOR_PERSONAL(6, "Sectia Antrenori Personali"),
    FITNESS(7, "Sectia Fitness");

    private final int id;
    private final String denumire;

    TipSectie(int id, String denumire) {
        this.id = id;
        this.denumire = denumire;
    }

    public int getId() { 
        return id; 
    }
    public String getDenumire() { 
        return denumire; 
    }

    public static TipSectie fromId(int id) {
        for (TipSectie t : values()) {
            if (t.id == id) return t;
        }
        throw new IllegalArgumentException("ID sectie invalid: " + id);
    }

    @Override
    public String toString() { return denumire; }
}