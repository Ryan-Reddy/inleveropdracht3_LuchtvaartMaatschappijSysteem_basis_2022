package inleverOpdracht3.Praxa;


import java.time.LocalDate;
import java.util.ArrayList;

public class Exemplaar {
    ArrayList<VerhuurRegel> regels = new ArrayList<>();
    private String details;
    private int serieNr;
    private Boolean status;  // TODO ??? details

    public Exemplaar(String details) {
        this.details = details;
    }

    public String geefDetails() {
        return this.details;
    }

    public boolean isVerhuurd() {
        for (VerhuurRegel verhuurRegel : regels) {
            if (verhuurRegel.geefPeriode().datumValtInPeriode(LocalDate.now())) {
                return true;
            }
        }
        return false;
    }

    public void voegVerhuurRegelToe(VerhuurRegel verhuurRegel) {
        this.regels.add(verhuurRegel);
    }
}
