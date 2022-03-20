package inleverOpdracht3.Praxa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VerhuurTransactie extends Transactie {
    private final List<VerhuurRegel> verhuurRegels = new ArrayList<>();
    private final VerhuurPeriode verhuurPeriode;

    public VerhuurTransactie(LocalDate verhuurDatum, LocalDate inleverDatum) {
        this.verhuurPeriode = new VerhuurPeriode(verhuurDatum, inleverDatum);
    }

    public void voegVerhuurRegelToe(Exemplaar exemplaar) {
        this.verhuurRegels.add(new VerhuurRegel(exemplaar, this));
    }

    public VerhuurPeriode getVerhuurPeriode() {
        return verhuurPeriode;
    }
}
