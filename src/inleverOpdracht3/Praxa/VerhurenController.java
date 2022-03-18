package inleverOpdracht3.Praxa;

import java.util.List;

public class VerhurenController {
    public List<String> start() {
       return VerhuurProduct.geefAlle();
    }

    public List<Exemplaar> selecteerProduct(VerhuurProduct keuze) {
        return keuze.geefBeschikbareExemplaren();
    }

    public void verhuurExemplaar() {
//        TODO
    }
}
