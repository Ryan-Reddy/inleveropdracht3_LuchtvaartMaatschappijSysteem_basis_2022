package inleverOpdracht3.Praxa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VerhuurTransactie {
    private LocalDate verhuurDatum;
    private LocalDate inleverDatum;

    public String geefDatum() {

      /* return String.format("verhuurDatum is %t",verhuurDatum) + String.format("name is %t",inleverDatum);*/
      return verhuurDatum.toString() + inleverDatum.toString();
    }

    @Override
    public boolean compareTo(Object o){
        if(o instanceof )

    }

    public VerhuurPeriode getVerhuurPeriode() {
        return verhuurPeriode;
    }
}
