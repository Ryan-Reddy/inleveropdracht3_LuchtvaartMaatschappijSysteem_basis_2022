package test;

import main.domeinLaag.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class VluchtTest {

    static LuchtvaartMaatschappij lvm;
    static Fabrikant f1;
    static VliegtuigType vtt1;
    static Vliegtuig vt1;
    static Luchthaven lh1, lh2;
    static Vlucht vl1, vl2;

    int todayYear = LocalDate.now().getYear();
    int todayMonth = LocalDate.now().getMonthValue();
    int todayDate = LocalDate.now().getDayOfMonth();
    int todayHour = LocalDateTime.now().getHour();
    int todayMinute = LocalDateTime.now().getMinute();
    int todaySecond = LocalDateTime.now().getSecond();

    static Calendar vertr = Calendar.getInstance();
    static Calendar aank = Calendar.getInstance();

    @BeforeEach
    public void initialize() {
        try {

            lvm = new LuchtvaartMaatschappij("NLM");
            f1 = new Fabrikant("Airbus", "G. Dejenelle");
            vtt1 = f1.creeervliegtuigtype("A-200", 140);
            Calendar datum = Calendar.getInstance();
            datum.set(2000, 01, 01);
            vt1 = new Vliegtuig(lvm, vtt1, "Luchtbus 100", datum);
            Land l1 = new Land("Nederland", 31);
            Land l2 = new Land("België", 32);
            lh1 = new Luchthaven("Schiphol", "ASD", true, l1);
            lh2 = new Luchthaven("Tegel", "TEG", true, l2);
            vertr.set(2020, 03, 30, 14, 15, 0);
            aank.set(2020, 03, 30, 15, 15, 0);
            vl1 = new Vlucht(vt1, lh1, lh2, vertr, aank);
            vertr.set(2020, 4, 1, 8, 15, 0);
            aank.set(2020, 4, 1, 9, 15, 0);
            vl2 = new Vlucht(vt1, lh1, lh2, vertr, aank);
        } catch (Exception e) {
            String errorMessage = "Exception: " + e.getMessage();
            System.out.println(errorMessage);
        }
    }

    /**
     * Business rule:
     * De bestemming moet verschillen van het vertrekpunt van de vlucht.
     */

    @Test
    public void test_Bestemming_GelijkZijnAanVertrek_MagNiet() {
        Vlucht vlucht = new Vlucht();
        try {
            vlucht.zetVliegtuig(vt1);
            vlucht.zetVertrekpunt(lh1);
            Luchthaven bestemming = vlucht.getBestemming();
            assertNull(bestemming);
            vlucht.zetBestemming(lh1);
            // De test zou niet verder mogen komen: er moet al een exception gethrowd zijn.
            bestemming = vlucht.getBestemming();
            assertNotEquals(bestemming, lh1);

        } catch (IllegalArgumentException e) {
            Luchthaven bestemming = vlucht.getBestemming();
            assertNotEquals(bestemming, lh1);
        }
    }

    @Test
    public void test_Bestemming_Verschilt_Vertrek_MagWel() {
        Vlucht vlucht = new Vlucht();
        Luchthaven bestemming;
        try {
            vlucht.zetVliegtuig(vt1);
            vlucht.zetVertrekpunt(lh2);
            bestemming = vlucht.getBestemming();
            assertNull(bestemming);
            vlucht.zetBestemming(lh1);
            bestemming = vlucht.getBestemming();
            assertEquals(bestemming, lh1);

        } catch (IllegalArgumentException e) {
            bestemming = vlucht.getBestemming();
            assertEquals(bestemming, lh1);
        }
    }

    /**
     * Business rule 2:
     * De vertrektijd en aankomsttijd moeten geldig zijn (dus een bestaande dag/uur/minuten combinatie aangeven) en in de toekomst liggen.
     * Zo niet: leg de vertrektijd en/of aankomsttijd niet vast (en geef een melding).
     */

    @Test
    public void testVertrekTijdOngeldigeDatum_magNiet() {
        vertr.set(204454, 70, 300, 14, 15, 0);
        aank.set(2020, 6, 30, 15, 15, 0);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, lh1, lh2, vertr, aank));
    }

    @Test
    public void testAankomstTijdOngeldigeDatum_magNiet() {
        vertr.set(2023, 9, 21, 14, 15, 0);
        aank.set(204454, 70, 300, 15, 15, 0);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, lh1, lh2, vertr, aank));
    }



    @Test
    public void testVertrekJaarInVerleden_magNiet() {
        assertThrows(VluchtException.class, () -> vertr.set(todayYear-10, todayMonth, todayDate, todayHour, todayMinute, todaySecond));
    }

    @Test
    public void testAankomstJaarInVerleden_magNiet() {
        assertThrows(VluchtException.class, () -> aank.set(todayYear-10, todayMonth, todayDate, todayHour, todayMinute, todaySecond));
    }

    @Test
    public void testVertrekMaandInVerleden_magNiet() {
        assertThrows(VluchtException.class, () -> vertr.set(todayYear, todayMonth-1, todayDate, todayHour, todayMinute, todaySecond));
    }

    @Test
    public void testAankomst_Maand_InVerleden_magNiet() {
        assertThrows(VluchtException.class, () -> aank.set(todayYear, todayMonth-1, todayDate, todayHour, todayMinute, todaySecond));
    }

    @Test
    public void testVertrekDagInVerleden_magNiet() {
        assertThrows(VluchtException.class, () -> vertr.set(todayYear, todayMonth, todayDate-12, todayHour, todayMinute, todaySecond));
    }

    @Test
    public void testAankomstDagInVerleden_magNiet() {
        assertThrows(VluchtException.class, () -> aank.set(todayYear, todayMonth, todayDate-1, todayHour, todayMinute, todaySecond));
    }

    /**
     * Business rule:
     * 3 De aankomsttijd moet na de vertrektijd liggen.
     * Zo niet: leg de aankomsttijd niet vast (en geef een melding).
     */

    @Test
    public void testAankomstenVertrek_Datum_MagNietGelijk() {
        vertr.set(todayYear, todayMonth, todayDate, todayHour, todayMinute, todaySecond);
        aank.set(todayYear, todayMonth, todayDate, todayHour, todayMinute, todaySecond);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, lh1, lh2, vertr, aank));
    }

    @Test
    public void testAankomstTijd_Jaar_MagNietVoorVertrekJaar() {
        vertr.set(todayYear+1, 03, 30, 15, 15, 0);
        aank.set(todayYear, 03, 30, 15, 15, 0);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, lh1, lh2, vertr, aank));
    }

    @Test
    public void testAankomstTijd_Maand_MagNietVoorVertrekMaand() {
        vertr.set(2023, todayMonth+1, 30, 15, 15, 0);
        aank.set(2023, todayMonth, 30, 15, 15, 0);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, lh1, lh2, vertr, aank));
    }

    @Test
    public void testAankomstTijd_Dag_MagNietVoorVertrekDag() {
        vertr.set(2023, 03, todayDate+1, 15, 15, 0);
        aank.set(2023, 03, todayDate, 15, 15, 0);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, lh1, lh2, vertr, aank));
    }

    @Test
    public void testAankomstTijd_Uur_MagNietVoorVertrektijd() {
        vertr.set(2023, 03, 30, todayHour+1, 15, 0);
        aank.set(2023, 03, 30, todayHour, 15, 0);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, lh1, lh2, vertr, aank));
    }

    @Test
    public void testAankomstTijd_Minuut_MagNietVoorVertrektijd() {

        vertr.set(2023, 03, 30, 15, todayMinute+1, 0);
        aank.set(2023, 03, 30, 15, todayMinute, 0);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, lh1, lh2, vertr, aank));
    }

    @Test
    public void testAankomstTijd_Seconde_MagNietVoorVertrektijd() {
        vertr.set(2023, 03, 30, 15, 15, todaySecond+1);
        aank.set(2023, 03, 30, 15, 15, todaySecond);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, lh1, lh2, vertr, aank));
    }

    /**
     * Business rule:
     * 4)	Een vliegtuig kan maar voor één vlucht tegelijk gebruikt worden.
     * Zo niet: leg de vertrektijd en/of aankomsttijd niet vast (en geef een melding).
     */


    @Test
    public void testVliegtuigInGebruik() {
        vertr.set(2020, 4, 1, 8, 15, 0);
        aank.set(2020, 4, 1, 9, 15, 0);
        Vlucht vlTest1 = new Vlucht(vt1, lh1, lh2, vertr, aank);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, lh1, lh2, vertr, aank));
    }

    /**
     * Business rule:
     * 5)	Een vlucht mag alleen geaccepteerd worden als de volgende gegevens zijn vastgelegd: vliegtuig, vertrekpunt, bestemming, vertrektijd, aankomsttijd.
     * Zo niet: bewaar de vlucht niet (en geef een melding).
     */


    @Test
    public void testAlleGegevensIngevuldVlucht_mistAankomsttijd_throwsExceptionMissendeGegevens() {
        vertr.set(2020, 03, 30, 14, 15, 0);
        aank.set(2020, 03, 30, 15, 15, 0);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, lh1, lh2, vertr, null));

    }

      @Test
    public void testAlleGegevensIngevuldVlucht_mistVertrekTijd_throwsExceptionMissendeGegevens() {
        vertr.set(2020, 03, 30, 14, 15, 0);
        aank.set(2020, 03, 30, 15, 15, 0);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, lh1, lh2, null, aank));

    }

      @Test
    public void testAlleGegevensIngevuldVlucht_mistBestemming_throwsExceptionMissendeGegevens() {
        vertr.set(2020, 03, 30, 14, 15, 0);
        aank.set(2020, 03, 30, 15, 15, 0);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, lh1, null, vertr, aank));

    }

      @Test
    public void testAlleGegevensIngevuldVlucht_mistVertrekpunt_throwsExceptionMissendeGegevens() {
        vertr.set(2020, 03, 30, 14, 15, 0);
        aank.set(2020, 03, 30, 15, 15, 0);

        assertThrows(VluchtException.class, () -> new Vlucht(vt1, null, lh2, vertr, aank));

    }

      @Test
    public void testAlleGegevensIngevuldVlucht_mistVliegtuig_throwsExceptionMissendeGegevens() {
        vertr.set(2020, 03, 30, 14, 15, 0);
        aank.set(2020, 03, 30, 15, 15, 0);

        assertThrows(VluchtException.class, () -> new Vlucht(null, lh1, lh2, vertr, aank));
    }

    @Test
    public void testAlleGegevensIngevuldVlucht_doesNotThrow() {
        vertr.set(2020, 03, 30, 14, 15, 0);
        aank.set(2020, 03, 30, 15, 15, 0);

        assertDoesNotThrow(() -> new Vlucht(vt1, lh1, lh2, vertr, aank));
    }
}
//    ==============================================================================
