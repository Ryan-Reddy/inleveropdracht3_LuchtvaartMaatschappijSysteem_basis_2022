package inleverOpdracht3.Praxa;

import java.time.LocalDate;
import java.util.List;

public class VerhurenController {

    public List<String> start() {
       return VerhuurProduct.geefAlle();
    }

    public List<String> selecteerProduct(VerhuurProduct keuze) {
        return keuze.geefBeschikbareExemplaren();
    }

    public void verhuurExemplaar(Exemplaar exemplaar, VerhuurTransactie verhuurTransactie, LocalDate verhuurDatum, LocalDate inleverDatum) {
        if (verhuurTransactie != null) {
            verhuurTransactie = new VerhuurTransactie(verhuurDatum, inleverDatum);
        }
        verhuurTransactie.voegVerhuurRegelToe(exemplaar);
    }
}
