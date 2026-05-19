package com.practica.enums;

public enum TipAbonament {
    BASIC(1, "Basic", 450.00),
    FULL_PASS(2, "Full Pass", 800.00),
    YOGA_ZEN(3, "Yoga & Zen", 600.00),
    COMBAT_MIX(4, "Combat Mix", 550.00),
    AQUA_SPORT(5, "Aqua Sport", 1200.00),
    PERSONAL_VIP(6, "Personal VIP", 1500.00),
    STUDENT_FIT(7, "Student Fit", 350.00),
    FAMILY_PACK(8, "Family Pack", 1800.00),
    WEEKEND_ONLY(9, "Weekend Only", 300.00),
    ANNUAL_PRO(10, "Annual Pro", 6500.00);

    private final int id;
    private final String denumire;
    private final double pret;

    TipAbonament(int id, String denumire, double pret) {
        this.id = id;
        this.denumire = denumire;
        this.pret = pret;
    }

    public int getId() { return id; }
    public String getDenumire() { return denumire; }
    public double getPret() { return pret; }


    public static TipAbonament fromId(int id) {
        for (TipAbonament t : values()) {
            if (t.id == id) return t;
        }
        throw new IllegalArgumentException("ID abonament invalid: " + id);
    }

    public static TipAbonament fromDenumire(String denumire) {
        for (TipAbonament t : values()) {
            if (t.denumire.equalsIgnoreCase(denumire)) return t;
        }
        return null; 
    }

    @Override
    public String toString() { return denumire; }
}