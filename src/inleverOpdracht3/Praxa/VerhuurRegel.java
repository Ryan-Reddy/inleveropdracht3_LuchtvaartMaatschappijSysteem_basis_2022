package inleverOpdracht3.Praxa;

import java.util.Date;

public class VerhuurRegel extends TransactieRegel {
    private VerhuurTransactie verhuurTransactie;
    private Date eindDatum;
    private final Exemplaar exemplaar;

    public VerhuurRegel(Exemplaar exemplaar, VerhuurTransactie verhuurTransactie) {
        this.exemplaar = exemplaar;
        exemplaar.voegVerhuurRegelToe(this);
        this.verhuurTransactie = verhuurTransactie;
    }

    public VerhuurPeriode geefPeriode() {
        return verhuurTransactie.getVerhuurPeriode();
    }
}

