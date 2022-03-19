package main.userInterfaceLaag;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.domeinLaag.Luchthaven;
import main.domeinLaag.Vliegtuig;
import main.domeinLaag.Vlucht;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class BoekVluchtController implements Initializable {
    @FXML
    private ComboBox<String> vertrekpuntComboBox;
    @FXML
    private ComboBox<String> aankomstpuntComboBox;
    @FXML
    private Button bevestigButton;
    @FXML
    private Button annulerenButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField tijdTextField;
    @FXML
    private Button selecteerDatumTijdButton;
    @FXML
    private ComboBox<String> stoelenComboBox;
    @FXML
    private ComboBox<String> klasseComboBox;
    @FXML
    private TextField voornaamTextField;
    @FXML
    private TextField achternaamTextField;
    @FXML
    private TextField straatTextField;
    @FXML
    private TextField huisnummerTextField;
    @FXML
    private TextField postcodeTextField;
    @FXML
    private TextField woonplaatsTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private TableView<String> tableVluchten;

    @FXML
    private TableView<OverzichtVluchtenDataModel> tableView;
    @FXML
    private TableColumn<OverzichtVluchtenDataModel, String> vliegtuigCol;
    @FXML
    private TableColumn<OverzichtVluchtenDataModel, String> vertrekpuntCol;
    @FXML
    private TableColumn<OverzichtVluchtenDataModel, String> bestemmingCol;
    @FXML
    private TableColumn<OverzichtVluchtenDataModel, String> vertrekTijdCol;
    @FXML
    private TableColumn<OverzichtVluchtenDataModel, String> aankomstTijdCol;


//    private LuchtvaartMaatschappij lvm = LuchtvaartMaatschappij.getCurrentLuchtvaartMaatschappij();
//    private VliegtuigType vliegtuigType;
//    private Vliegtuig vliegtuig;
//    private Fabrikant fabrikant;

    private TreeMap<Integer, Vlucht> vluchtenMap;

//    observable list voor table
    private ObservableList<OverzichtVluchtenDataModel> dataList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vertrekpuntComboBox.setItems(FXCollections.observableList(Luchthaven.geefAlle().keySet().stream().toList()));
        aankomstpuntComboBox.setItems(FXCollections.observableList(Luchthaven.geefAlle().keySet().stream().toList()));

        vliegtuigCol.setCellValueFactory(new PropertyValueFactory<OverzichtVluchtenDataModel, String>("vliegtuigNm"));
//        vertrekpuntCol.setCellValueFactory(new PropertyValueFactory<OverzichtVluchtenDataModel, String>("vertrekpunt"));
//        bestemmingCol.setCellValueFactory(new PropertyValueFactory<OverzichtVluchtenDataModel, String>("bestemming"));
        vertrekTijdCol.setCellValueFactory(new PropertyValueFactory<OverzichtVluchtenDataModel, String>("vertrekTijd"));
        aankomstTijdCol.setCellValueFactory(new PropertyValueFactory<OverzichtVluchtenDataModel, String>("aankomstTijd"));
        tableView.setItems(dataList);


//        vluchtenMap = Vlucht.geefAlle();
//        fabrikantenMap = Fabrikant.geefAlle();
//        vliegtuig = new Vliegtuig(lvm);
//
//
//        /** Update de UI: Toon de fabrikanten in de fabrikantComboBox
//         * Toon eerst een leeg, zodat de gebruiker een waarde moet
//         * selecteren, wat de event triggered. */
//        String leeg = "";
//        fabrikantComboBox.getItems().add(leeg);
//        // Creëer een gesorteerde Set namen en voeg ze toe aan fabrikantComboBox.
//        Set<String> fabrikantenSet = fabrikantenMap.keySet();
//        for (String fabrikant : fabrikantenSet) {
//            fabrikantComboBox.getItems().add(fabrikant);
//        }
//        // Voeg event listener toe aan naamTextField. Roep naam() als focus-lost event optreedt.
//        naamTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> focusProperty, Boolean oldValue, Boolean newValue) {
//                if (!newValue) // if (focus lost)
//                    naam();
//            }
//        });
//        /*
//        Een nieuwe, kortere manier om bovenstaande te doen is met een lambda:
//        naamTextField.focusedProperty().addListener((obs, oldValue, newValue) -> {
//            if(!newValue) naam();});
//        */

    }

    public void luchthavensVeranderd() {
        System.out.println("hi");
        String gekozenVertrekPunt = vertrekpuntComboBox.getValue();
        String gekozenAankomstPunt = aankomstpuntComboBox.getValue();
        if (vertrekpuntComboBox.getValue() != null && aankomstpuntComboBox.getValue() != null) {
            System.out.println("hi v2");
            Luchthaven vertrekPunt = Luchthaven.geefAlle().get(gekozenVertrekPunt);
            Luchthaven aankomstPunt = Luchthaven.geefAlle().get(gekozenAankomstPunt);
            dataList = FXCollections.observableArrayList();
            TreeMap<Integer, Vlucht> vluchten = Vlucht.geefAlle();
            Set<Integer> vluchtenSet = vluchten.keySet();
            for (Integer vluchtNr : vluchtenSet) {
                Vlucht vlucht = vluchten.get(vluchtNr);
                if (vlucht.getVertrekPunt().equals(vertrekPunt) && vlucht.getBestemming().equals(aankomstPunt)) {
                    System.out.println("hi v3");
                    OverzichtVluchtenDataModel dataModel;
                    Vliegtuig vt = vlucht.getVliegtuig();
                    String vliegtuig = vt.geefNaam();
                    String vertrekp = vlucht.getVertrekPunt().geefNaam();
                    String bestemming = vlucht.getBestemming().geefNaam();
                    Calendar vertrekTijd = vlucht.getVertrekTijd();
                    Calendar aankomstTijd = vlucht.getAankomstTijd();
                    dataModel = new OverzichtVluchtenDataModel(vliegtuig, vertrekp, bestemming, vertrekTijd, aankomstTijd);
                    dataList.add(dataModel);
                }
            }
            tableView.setItems(dataList);
        }
    }

    public void fabrikant() {
//        String fabrikantNaam = (String) fabrikantComboBox.getValue();
//        if (fabrikantNaam != null) {
//            //Zoek het fabrikant-object op basis van de naam en onthoud het geselecteerde fabrikant
//            this.fabrikant = fabrikantenMap.get(fabrikantNaam);
//            if (fabrikant != null) {
//                vliegtuigTypenMap = fabrikant.geefVliegtuigTypen();
//                // Update de UI
//                // Toon de vliegtuigtypes van deze fabrikant in de combo box; begin met een leeg veld
//                String leeg = "";
//                typeCombobox.getItems().clear();
//                typeCombobox.getItems().add(leeg);
//                Set<String> vliegtuigTypenSet = vliegtuigTypenMap.keySet();
//                for (String vliegtuigtypeNaam : vliegtuigTypenSet) {
//                    typeCombobox.getItems().add(vliegtuigtypeNaam);
//                }
//                // Toon de contactpersoon van de fabrikant
//                String cp = this.fabrikant.geefContactpersoon();
//                contactTextfield.setText(cp);
//            } else {
//                contactTextfield.setText("");
//                typeCombobox.getItems().clear();
//                capaciteitTextField.setText("");
//                vliegtuig.zetVliegtuigType(null);
//            }
//        }
    }

    public void type() {
//        String vttCode = (String) typeCombobox.getValue();
//        //Zoek het vliegtuigtype-object, op basis van de code.
//        if (vttCode != null) {
//            this.vliegtuigType = vliegtuigTypenMap.get(vttCode);
//            if (vliegtuigType != null) {
//                vliegtuig.zetVliegtuigType(vliegtuigType);
//                // Update de UI: Toon de capaciteit
//                int cap = this.vliegtuigType.geefCapaciteit();
//                capaciteitTextField.setText(String.valueOf(cap));
//            } else {
//                vliegtuig.zetVliegtuigType(null);
//                capaciteitTextField.setText("");
//            }
//        }
    }

    public void naam() {
//        naamTextField.setStyle("-fx-text-fill: black;");
//        String naam = naamTextField.getText();
//        if (naam != null && !naam.isEmpty()) {
//            try {
//                vliegtuig.zetNaam(naam);
//            } catch (IllegalArgumentException ei) {
//                toonMelding(ei.getMessage());
//                naamTextField.setStyle("-fx-text-fill: red;");
//            }
//        } else {
//            vliegtuig.zetNaam(null);
//            naamTextField.setText("");
//        }
    }

    public void inGebruik() {
//        LocalDate inGebruik = InGebruikDatePicker.getValue();
//        if (inGebruik != null) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.clear();
//            calendar.set(inGebruik.getYear(), inGebruik.getMonthValue() - 1, inGebruik.getDayOfMonth());
//            try {
//                vliegtuig.zetInGebruik(calendar);
//            } catch (IllegalArgumentException ei) {
//                toonMelding(ei.getMessage());
//            }
//        }
    }

    public void bevestig() {
        System.out.println("Beeld je in dat de boeking wordt opgeslagen...");
//        try {
//            vliegtuig.bewaar();
//            Stage stage = (Stage) buttonOK.getScene().getWindow();
//            stage.close();
//        } catch (IllegalStateException ei) {
//            toonMelding(ei.getMessage());
//        }
    }

    public void annuleren() {
        Stage stage = (Stage) annulerenButton.getScene().getWindow();
        stage.close();
    }

    private void toonMelding(String tekstMessage) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Waarschuwing!");
//        alert.setContentText(tekstMessage);
//        alert.showAndWait();
    }
}
