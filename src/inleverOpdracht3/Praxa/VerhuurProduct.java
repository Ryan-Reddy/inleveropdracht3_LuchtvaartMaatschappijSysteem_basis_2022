package inleverOpdracht3.Praxa;

import java.util.ArrayList;
import java.util.List;

public class VerhuurProduct extends Product {
    private double verhuurPrijs;
    private double borg;
    private static List<VerhuurProduct> alleVerhuurProducten;
    private String omschrijving;
    private List<Exemplaar> exemplaren;

    public static List<String> geefAlle() {
        List<String> alle = new ArrayList<>();
        for (VerhuurProduct verhuurProduct : alleVerhuurProducten) {
            String omschrijving = verhuurProduct.geefOmschrijving();
            alle.add(omschrijving);
        }
        return alle;
    }

    public List<Exemplaar> geefBeschikbareExemplaren() {
        List<Exemplaar> beschikbareExemplaren = new ArrayList<>();
        for (Exemplaar exemplaar : this.exemplaren) {
            if (!exemplaar.isVerhuurd()) {
                beschikbareExemplaren.add(exemplaar);
            }
        }
        return beschikbareExemplaren;
    }

    public String geefOmschrijving() {
        return omschrijving;
    }
}
