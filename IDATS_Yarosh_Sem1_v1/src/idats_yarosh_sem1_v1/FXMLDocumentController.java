package idats_yarosh_sem1_v1;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import kolekce.*;
import sprava.*;
import terapie.*;

public class FXMLDocumentController implements Initializable {

    private final AnchorPane bgForDialog = new AnchorPane();

    @FXML
    private ListView<Termin> listView;
    @FXML
    private Label labelForWorkHours;
    private Terapeut therapist = Terapeut.EMPTY_TERAPEUT;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private String name = "";
    private String surname = "";
    private Integer workFromHour = PracovniDoba.STANDARDNI_DOBA.getBegin();
    private Integer workToHour = PracovniDoba.STANDARDNI_DOBA.getEnd();

    @FXML
    private void enterTerapeutData(ActionEvent event) {
        Dialog<ButtonType> inputDialog = new Dialog<>(); //create dialog

        bgForDialog.setPrefSize(300, 240);//set dialog size
        inputDialog.getDialogPane().setContent(bgForDialog);

        createLabels(); //create a group of labels
        List<TextField> textFields = createTextFields(); //create a group of text fields

        inputDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);//add to the dialog 2 buttons

        inputDialog.showAndWait().filter(btnType -> btnType == ButtonType.OK).ifPresent(btnType -> {
            name = textFields.get(0).getText();
            surname = textFields.get(1).getText();
            try {
                workFromHour = textFields.get(2).getText().isEmpty() ? workFromHour : Integer.parseInt(textFields.get(2).getText());
                workToHour = textFields.get(3).getText().isEmpty() ? workToHour : Integer.parseInt(textFields.get(3).getText());
            } catch (NumberFormatException e) {
                repeatInput(event, "Error", "Wrong number format", Alert.AlertType.ERROR);
            }
            if (!isInDayInterval(workFromHour) || !isInDayInterval(workToHour)) {
                repeatInput(event, "Error", "Unexisting hours", Alert.AlertType.ERROR);
            } else if (name.isEmpty() || surname.isEmpty()) {
                repeatInput(event, "Empty fields", "You have one or more blank fields. Please, fill them.", Alert.AlertType.INFORMATION);
            } else {
                therapist = new Terapeut(new Person(name, surname), new PracovniDoba(workFromHour, workToHour));
                showLabelWithWorkingHours();
            }
        });
    }

    private boolean isInDayInterval(final int hour) {
        return hour >= 0 && hour <= 24;
    }

    private void repeatInput(
            final ActionEvent event,
            final String titleText,
            final String contextText,
            final Alert.AlertType alertType) {
        callAlertWindow(titleText, contextText, alertType);
        enterTerapeutData(event);
    }

    private void showLabelWithWorkingHours() {
        labelForWorkHours.setText(String.format("Therapist is working from %d to %d hours.", workFromHour, workToHour));
        labelForWorkHours.setVisible(true);
    }

    private Alert callAlertWindow(
            final String titleText,
            final String contextText,
            final Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titleText);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
        return alert;
    }

    private TextField createTextField(
            final double topPoint,
            final double leftPoint,
            final String text) {
        TextField textField = new TextField(text);
        AnchorPane.setTopAnchor(textField, topPoint);
        AnchorPane.setLeftAnchor(textField, leftPoint);
        bgForDialog.getChildren().add(textField);
        return textField;
    }

    private List createTextFields() {
        List<TextField> fields = Arrays.asList(
                createTextField(50.0, 100.0, name),
                createTextField(90.0, 100.0, surname),
                createTextField(130.0, 100.0, workFromHour.toString()),
                createTextField(170.0, 100.0, workToHour.toString()));
        return fields;
    }

    private Label createLabel(
            final String text,
            final double topPoint,
            final double leftPoint) {
        Label label = new Label(text);
        AnchorPane.setTopAnchor(label, topPoint);
        AnchorPane.setLeftAnchor(label, leftPoint);
        bgForDialog.getChildren().add(label);
        return label;
    }

    private void createLabels() {
        createLabel("Name:", 55.0, 10.0);
        createLabel("Surname:", 95.0, 10.0);
        createLabel("Work from:", 135.0, 10.0);
        createLabel("Work to:", 175.0, 10.0);
    }
}
