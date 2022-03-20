package inleverOpdracht3.Praxa;

public class MainApp {
    private static final VerhurenController verhurenController = new VerhurenController();

    public static void main(String[] args) {
        verhurenController.start().forEach(System.out::println);
        verhurenController.selecteerProduct(VerhuurProduct.vindProduct("schroef")).forEach(System.out::println);

    }
}
