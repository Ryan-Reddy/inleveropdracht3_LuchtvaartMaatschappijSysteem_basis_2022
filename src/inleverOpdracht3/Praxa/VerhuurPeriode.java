package inleverOpdracht3.Praxa;

import java.time.LocalDate;

public class VerhuurPeriode {
    private final LocalDate verhuurDatum;
    private final LocalDate inleverDatum;

    public VerhuurPeriode(LocalDate verhuurDatum, LocalDate inleverDatum) {
        this.verhuurDatum = verhuurDatum;
        this.inleverDatum = inleverDatum;
    }

    public boolean datumValtInPeriode(LocalDate datum) {
        return verhuurDatum.compareTo(datum) <= 0 && inleverDatum.compareTo(datum) >= 0;
    }
}
