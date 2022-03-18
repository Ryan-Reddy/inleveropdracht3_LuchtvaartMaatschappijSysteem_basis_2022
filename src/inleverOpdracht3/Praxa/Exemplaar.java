package inleverOpdracht3.Praxa;


import java.util.ArrayList;
import java.time.Date;

public class Exemplaar {
    private String details;
    private boolean verhuurd;
    ArrayList<VerhuurRegel> verhuurRegels = new ArrayList<>();


    public String geefDetails() {
        return this.details;
    }

    public boolean isVerhuurd() {
        for(VerhuurRegel r : this.verhuurRegels ){
            if (r.geefPeriode() <   ){
               Date

                return true;
            };
        }

        return this.verhuurd;
    }
}
