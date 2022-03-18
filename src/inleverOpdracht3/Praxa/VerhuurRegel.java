package inleverOpdracht3.Praxa;

import jdk.vm.ci.meta.Local;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class VerhuurRegel {
    private Period periode;


    public Period geefPeriode(LocalDate startDate, LocalDate endDate){
        for (verhuring:VerhuurTransactie) {

        }
        Period period = Period.between(startDate, endDate);
        return period;
    }
}

