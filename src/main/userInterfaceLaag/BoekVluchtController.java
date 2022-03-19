package main.userInterfaceLaag;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
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


    private boolean luchtHavenVeranderdBOOL = false;

    private TreeMap<Integer, Vlucht> vluchtenMap;

    private ObservableList<OverzichtVluchtenDataModel> dataList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vertrekpuntComboBox.setItems(FXCollections.observableList(Luchthaven.geefAlle().keySet().stream().toList()));
        aankomstpuntComboBox.setItems(FXCollections.observableList(Luchthaven.geefAlle().keySet().stream().toList()));


        vliegtuigCol.setCellValueFactory(new PropertyValueFactory<OverzichtVluchtenDataModel, String>("vliegtuigNm"));
        vertrekTijdCol.setCellValueFactory(new PropertyValueFactory<OverzichtVluchtenDataModel, String>("vertrekTijd"));
        aankomstTijdCol.setCellValueFactory(new PropertyValueFactory<OverzichtVluchtenDataModel, String>("aankomstTijd"));
        tableView.setItems(dataList);


    }

    public void luchthavensVeranderd() {
        System.out.println("hi");
        String gekozenVertrekPunt = vertrekpuntComboBox.getValue();
        String gekozenAankomstPunt = aankomstpuntComboBox.getValue();
        if (vertrekpuntComboBox.getValue() != null && aankomstpuntComboBox.getValue() != null) {
            luchtHavenVeranderdBOOL = true;
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


    public void bevestig() {
        String tekstMessage = "Checking bevestigen...";
        String title = "Bevestiging ontvangen.";
        errorLabel.setText(tekstMessage);
        if (luchtHavenVeranderdBOOL == true) {
            tekstMessage = "Beeld je in dat de boeking wordt opgeslagen...";
            errorLabel.setTextFill(Paint.valueOf("BLACK"));
            errorLabel.setText(tekstMessage);
            toonMelding(tekstMessage, title);
        } else {
            tekstMessage = "Gelieve een vertrek- en aankomstlocatie aan te geven..";
            errorLabel.setText(tekstMessage);
        }
    }

    public void annuleren() {
        Stage stage = (Stage) annulerenButton.getScene().getWindow();
        stage.close();
    }

    private void toonMelding(String tekstMessage, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(tekstMessage);
        alert.showAndWait();
    }
}
