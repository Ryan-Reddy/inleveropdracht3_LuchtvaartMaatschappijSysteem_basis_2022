package inleverOpdracht3.Praxa;

import java.util.ArrayList;
import java.util.List;

public class VerhuurProduct extends Product {
    private double verhuurPrijs;
    private double borg;
    private static List<VerhuurProduct> alleVerhuurProducten;
    private String omschrijving;
    private List<Exemplaar> exemplaren;

    static {
        VerhuurProduct.alleVerhuurProducten = new ArrayList<>();
        alleVerhuurProducten.add(new VerhuurProduct("schroef"));
        alleVerhuurProducten.add(new VerhuurProduct("bout"));
        alleVerhuurProducten.add(new VerhuurProduct("moer"));
        alleVerhuurProducten.add(new VerhuurProduct("ryan"));
        alleVerhuurProducten.add(new VerhuurProduct("boor"));
    }

    public static VerhuurProduct vindProduct(String omschrijving) {
        return alleVerhuurProducten.stream().filter(product -> product.omschrijving.equals(omschrijving)).findFirst().orElse(null);
    }

    public VerhuurProduct(String omschrijving) {
        this.omschrijving = omschrijving;
        this.exemplaren = new ArrayList<>();
        this.exemplaren.add(new Exemplaar(omschrijving + " 1"));
        this.exemplaren.add(new Exemplaar(omschrijving + " 2"));
        this.exemplaren.add(new Exemplaar(omschrijving + " 3"));
    }

    public static List<String> geefAlle() {
        List<String> alle = new ArrayList<>();
        for (VerhuurProduct verhuurProduct : alleVerhuurProducten) {
            String omschrijving = verhuurProduct.geefOmschrijving();
            alle.add(omschrijving);
        }
        return alle;
    }

    public List<String> geefBeschikbareExemplaren() {
        List<String> beschikbareExemplaren = new ArrayList<>();
        for (Exemplaar exemplaar : this.exemplaren) {
            if (!exemplaar.isVerhuurd()) {
                beschikbareExemplaren.add(exemplaar.geefDetails());
            }
        }
        return beschikbareExemplaren;
    }

    public String geefOmschrijving() {
        return omschrijving;
    }
}
