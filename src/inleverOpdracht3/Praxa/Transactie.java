package inleverOpdracht3.Praxa;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class Transactie {
    private List<TransactieRegel> regels;
    private int transactieNr;
    private LocalDate datum;
    private Time tijd;
    private String plaats;
    private int btwTotaal;
    private String transactieType;
    private Klant klant;

    public float getTotaal() {
        return this.regels.stream().reduce(0f, (prev, cur) -> prev + cur.getRegelTotaal(), Float::sum);
    }
}
