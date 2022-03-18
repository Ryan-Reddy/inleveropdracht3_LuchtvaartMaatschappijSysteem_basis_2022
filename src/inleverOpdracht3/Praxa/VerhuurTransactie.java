package inleverOpdracht3.Praxa;
import java.lang.String;

import jdk.vm.ci.meta.Local;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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
}

