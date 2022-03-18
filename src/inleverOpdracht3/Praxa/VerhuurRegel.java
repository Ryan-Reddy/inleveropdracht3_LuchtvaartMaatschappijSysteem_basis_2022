package inleverOpdracht3.Praxa;

import java.util.Date;

public class VerhuurRegel extends TransactieRegel {
    private VerhuurTransactie verhuurTransactie;
    private Date eindDatum;
    private final Exemplaar exemplaar;


    public Period geefPeriode(LocalDate startDate, LocalDate endDate){
        for (verhuring:VerhuurTransactie) {

        }
        Period period = Period.between(startDate, endDate);
        return period;
    }
}

