package com.practica;

import com.practica.enums.Rol;
import com.practica.enums.TipAbonament;
import com.practica.enums.TipSectie;
import com.practica.model.*;
import com.practica.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Club Sportiv.
 *
 * Tests cover:
 *  - Model constructors and getters / setters
 *  - Enum helpers (fromId, fromDenumire, toString)
 *  - Validator (happy-path + assertThrows for exceptions)
 *  - Utilizator helper method esteAdmin()
 */
public class AppTest {

    // ── shared fixtures ──────────────────────────────────────────────────────

    private Membru membru;
    private Antrenor antrenor;
    private Abonament abonament;
    private Sectie sectie;
    private Sedinta sedinta;
    private Utilizator admin;
    private Utilizator utilizatorNormal;

    @BeforeEach
    public void setUp() {
        abonament = new Abonament(
            1, "Basic", "Abonament de baza",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 31),
            450.00
        );

        sectie = new Sectie(1, TipSectie.FITNESS, "Sectia de fitness", 30);

        antrenor = new Antrenor(
            1, "Popescu", "Ion", "ion@club.md", "0698123456", 1, 7500.00
        );

        membru = new Membru(
            1, "Ionescu", "Maria", 28,
            "maria@club.md", "0712345678",
            1, 1, 1
        );

        sedinta = new Sedinta(
            1,
            LocalDate.of(2024, 6, 15),
            LocalTime.of(10, 30),
            1, 1
        );

        admin = new Utilizator(1, "admin", "secret", Rol.ADMIN, null);
        utilizatorNormal = new Utilizator(2, "maria", "pass", Rol.UTILIZATOR, 1);
    }

    // ── Abonament ─────────────────────────────────────────────────────────────

    @Test
    void testAbonamentGetters() {
        assertEquals(1, abonament.getIdAbonament());
        assertEquals("Basic", abonament.getDenumire());
        assertEquals("Abonament de baza", abonament.getDescriere());
        assertEquals(LocalDate.of(2024, 1, 1), abonament.getDataInceput());
        assertEquals(LocalDate.of(2024, 12, 31), abonament.getDataExpirarii());
        assertEquals(450.00, abonament.getPret(), 0.001);
    }

    @Test
    void testAbonamentSetters() {
        abonament.setDenumire("Full Pass");
        abonament.setPret(800.00);
        assertEquals("Full Pass", abonament.getDenumire());
        assertEquals(800.00, abonament.getPret(), 0.001);
    }

    @Test
    void testAbonamentGetTipAbonament() {
        assertEquals(TipAbonament.BASIC, abonament.getTipAbonament());
    }

    @Test
    void testAbonamentToString() {
        String s = abonament.toString();
        assertTrue(s.contains("Basic"));
        assertTrue(s.contains("450.0"));
    }

    // ── Antrenor ──────────────────────────────────────────────────────────────

    @Test
    void testAntrenorGetters() {
        assertEquals(1, antrenor.getIdAntrenor());
        assertEquals("Popescu", antrenor.getNumeAntrenor());
        assertEquals("Ion", antrenor.getPrenumeAntrenor());
        assertEquals("ion@club.md", antrenor.getEmailAntrenor());
        assertEquals("0698123456", antrenor.getTelefonAntrenor());
        assertEquals(1, antrenor.getIdSectie());
        assertEquals(7500.00, antrenor.getSalariu(), 0.001);
    }

    @Test
    void testAntrenorSetters() {
        antrenor.setNumeAntrenor("Georgescu");
        antrenor.setSalariu(9000.00);
        assertEquals("Georgescu", antrenor.getNumeAntrenor());
        assertEquals(9000.00, antrenor.getSalariu(), 0.001);
    }

    @Test
    void testAntrenorToStringContainsNume() {
        assertTrue(antrenor.toString().contains("Popescu"));
    }

    // ── Membru ────────────────────────────────────────────────────────────────

    @Test
    void testMembruGetters() {
        assertEquals(1, membru.getIdMembru());
        assertEquals("Ionescu", membru.getNume());
        assertEquals("Maria", membru.getPrenume());
        assertEquals(28, membru.getVarsta());
        assertEquals("maria@club.md", membru.getEmail());
        assertEquals("0712345678", membru.getTelefon());
        assertEquals(1, membru.getIdSectie());
        assertEquals(Integer.valueOf(1), membru.getIdAntrenor());
        assertEquals(1, membru.getIdAbonament());
    }

    @Test
    void testMembruSetters() {
        membru.setNume("Duma");
        membru.setVarsta(30);
        assertEquals("Duma", membru.getNume());
        assertEquals(30, membru.getVarsta());
    }

    @Test
    void testMembruIdAntrenorNullAllowed() {
        Membru fara = new Membru(2, "Test", "User", 25,
            "t@t.md", "0698000000", 1, null, 1);
        assertNull(fara.getIdAntrenor());
    }

    @Test
    void testMembruToString() {
        String s = membru.toString();
        assertTrue(s.contains("Ionescu"));
        assertTrue(s.contains("Maria"));
    }

    // ── Sectie ────────────────────────────────────────────────────────────────

    @Test
    void testSectieGetters() {
        assertEquals(1, sectie.getIdSectie());
        assertEquals(TipSectie.FITNESS, sectie.getTipSectie());
        assertEquals("Sectia de fitness", sectie.getDescriere());
        assertEquals(30, sectie.getCapacitateMaxima());
    }

    @Test
    void testSectieGetNumeSectie() {
        assertEquals("Sectia Fitness", sectie.getNumeSectie());
    }

    @Test
    void testSectieSetters() {
        sectie.setCapacitateMaxima(50);
        assertEquals(50, sectie.getCapacitateMaxima());
    }

    // ── Sedinta ───────────────────────────────────────────────────────────────

    @Test
    void testSedintaGetters() {
        assertEquals(1, sedinta.getIdSedinta());
        assertEquals(LocalDate.of(2024, 6, 15), sedinta.getData());
        assertEquals(LocalTime.of(10, 30), sedinta.getOra());
        assertEquals(1, sedinta.getIdAntrenor());
        assertEquals(1, sedinta.getIdMembru());
    }

    @Test
    void testSedintaSetters() {
        sedinta.setData(LocalDate.of(2025, 1, 10));
        assertEquals(LocalDate.of(2025, 1, 10), sedinta.getData());
    }

    // ── Utilizator ────────────────────────────────────────────────────────────

    @Test
    void testUtilizatorGetters() {
        assertEquals(1, admin.getIdUtilizator());
        assertEquals("admin", admin.getUsername());
        assertEquals("secret", admin.getParola());
        assertEquals(Rol.ADMIN, admin.getRol());
        assertNull(admin.getIdMembru());
    }

    @Test
    void testEsteAdminTrue() {
        assertTrue(admin.esteAdmin());
    }

    @Test
    void testEsteAdminFalse() {
        assertFalse(utilizatorNormal.esteAdmin());
    }

    @Test
    void testUtilizatorIdMembru() {
        assertEquals(Integer.valueOf(1), utilizatorNormal.getIdMembru());
    }

    // ── TipAbonament enum ─────────────────────────────────────────────────────

    @Test
    void testTipAbonamentFromId() {
        assertEquals(TipAbonament.BASIC, TipAbonament.fromId(1));
        assertEquals(TipAbonament.ANNUAL_PRO, TipAbonament.fromId(10));
    }

    @Test
    void testTipAbonamentFromIdInvalid() {
        assertThrows(IllegalArgumentException.class, () -> TipAbonament.fromId(999));
    }

    @Test
    void testTipAbonamentFromDenumire() {
        assertEquals(TipAbonament.FULL_PASS, TipAbonament.fromDenumire("Full Pass"));
        assertEquals(TipAbonament.STUDENT_FIT, TipAbonament.fromDenumire("student fit"));
    }

    @Test
    void testTipAbonamentFromDenumireNullWhenUnknown() {
        assertNull(TipAbonament.fromDenumire("Inexistent"));
    }

    @Test
    void testTipAbonamentPret() {
        assertEquals(1500.00, TipAbonament.PERSONAL_VIP.getPret(), 0.001);
        assertEquals(300.00,  TipAbonament.WEEKEND_ONLY.getPret(), 0.001);
    }

    // ── TipSectie enum ────────────────────────────────────────────────────────

    @Test
    void testTipSectieFromId() {
        assertEquals(TipSectie.CARDIO, TipSectie.fromId(1));
        assertEquals(TipSectie.FITNESS, TipSectie.fromId(7));
    }

    @Test
    void testTipSectieFromIdInvalid() {
        assertThrows(IllegalArgumentException.class, () -> TipSectie.fromId(99));
    }

    @Test
    void testTipSectieToString() {
        assertEquals("Sectia Cardio", TipSectie.CARDIO.toString());
        assertEquals("Sectia Box",    TipSectie.BOX.toString());
    }

    // ── Rol enum ──────────────────────────────────────────────────────────────

    @Test
    void testRolValues() {
        Rol[] roluri = Rol.values();
        assertEquals(2, roluri.length);
        assertEquals(Rol.ADMIN,      Rol.valueOf("ADMIN"));
        assertEquals(Rol.UTILIZATOR, Rol.valueOf("UTILIZATOR"));
    }

    // ── Validator ─────────────────────────────────────────────────────────────

    @Test
    void testValidareNumeValid() {
        assertDoesNotThrow(() -> Validator.validareNume("Maria"));
        assertDoesNotThrow(() -> Validator.validareNume("Ionescu"));
    }

    @Test
    void testValidareNumeGol() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validareNume(""));
    }

    @Test
    void testValidareNumeNull() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validareNume(null));
    }

    @Test
    void testValidareNumeCuCifre() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validareNume("Ion123"));
    }

    @Test
    void testValidareEmailValid() {
        assertDoesNotThrow(() -> Validator.validareEmail("test@club.md"));
        assertDoesNotThrow(() -> Validator.validareEmail("user.name+tag@example.com"));
    }

    @Test
    void testValidareEmailInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validareEmail("not-an-email"));
    }

    @Test
    void testValidareVarstaValid() {
        assertDoesNotThrow(() -> Validator.validareVarsta(18));
        assertDoesNotThrow(() -> Validator.validareVarsta(65));
    }

    @Test
    void testValidareVarstaNegativa() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validareVarsta(-5));
    }

    @Test
    void testValidareVarstaPreaМare() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validareVarsta(121));
    }

    @Test
    void testValidarePretValid() {
        assertDoesNotThrow(() -> Validator.validarePret(0.0));
        assertDoesNotThrow(() -> Validator.validarePret(999.99));
    }

    @Test
    void testValidarePretNegativ() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validarePret(-1.0));
    }

    @Test
    void testValidareDataValid() {
        assertDoesNotThrow(() -> Validator.validareData(LocalDate.now()));
    }

    @Test
    void testValidareDataNull() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validareData(null));
    }

    @Test
    void testValidareTelefonValid() {
        assertDoesNotThrow(() -> Validator.validareTelefon("0612345678"));
        assertDoesNotThrow(() -> Validator.validareTelefon("0712345678"));
    }

    @Test
    void testValidareTelefonInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validareTelefon("123456"));
    }

    @Test
    void testValidareTelefonGol() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validareTelefon(""));
    }

    @Test
    void testValidareCampGolValid() {
        assertDoesNotThrow(() -> Validator.validareCampGol("valoare", "camp"));
    }

    @Test
    void testValidareCampGolInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validareCampGol("   ", "camp"));
    }

    @Test
    void testValidareDataExpirareValid() {
        assertDoesNotThrow(() -> Validator.validareDataExpirare(
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 31)
        ));
    }

    @Test
    void testValidareDataExpirareInvalida() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validareDataExpirare(
            LocalDate.of(2025, 6, 1),
            LocalDate.of(2024, 1, 1)
        ));
    }

    @Test
    void testValidareDataExpirareNull() {
        assertThrows(IllegalArgumentException.class, () ->
            Validator.validareDataExpirare(null, LocalDate.now()));
    }

    @Test
    void testValidareSalariuValid() {
        assertDoesNotThrow(() -> Validator.validareSalariu(5000.0));
        assertDoesNotThrow(() -> Validator.validareSalariu(0.01));
    }

    @Test
    void testValidareSalariuZero() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validareSalariu(0.0));
    }

    @Test
    void testValidareSalariuNegativ() {
        assertThrows(IllegalArgumentException.class, () -> Validator.validareSalariu(-100.0));
    }
}