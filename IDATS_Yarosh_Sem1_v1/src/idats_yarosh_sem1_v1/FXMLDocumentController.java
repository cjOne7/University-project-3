package idats_yarosh_sem1_v1;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import kolekce.*;
import sprava.*;
import terapie.*;

public class FXMLDocumentController implements Initializable {

    private final AnchorPane bgForDialog = new AnchorPane();
    private Terapeut therapist = Terapeut.EMPTY_TERAPEUT;
    private String name = "";
    private String surname = "";
    private Integer workFrom = PracovniDoba.STANDARD_WORK_DAY.getBeginOfWorkDay();
    private Integer workTo = PracovniDoba.STANDARD_WORK_DAY.getEndOfWorkDay();
    private ObservableList<Node> rectangles;

    @FXML
    private ListView<Termin> listView;
    @FXML
    private Label labelForWorkHours;
    @FXML
    private DatePicker datePickerFrom;
    @FXML
    private DatePicker datePickerTo;
    @FXML
    private Pane paneForDates;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rectangles = paneForDates.getChildren();
        datePickerFrom.setValue(LocalDate.now());
        datePickerTo.setValue(LocalDate.now().plusDays(7));
    }

    @FXML
    private void enterTerapeutData(ActionEvent event) {
        Dialog<ButtonType> inputDialog = new Dialog<>(); //create dialog

        bgForDialog.setPrefSize(300, 240);//set dialog size
        inputDialog.getDialogPane().setContent(bgForDialog);

        createLabels(); //create a group of labels
        //create a group of text fields:
        //0 - name, 1 - surname, 2 - work from, 3 - work to
        List<TextField> textFields = createTextFields();

        inputDialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.OK, ButtonType.CANCEL);//add to the dialog 2 buttons

        inputDialog.showAndWait().filter(bType -> bType == ButtonType.OK).ifPresent(t -> {
            name = textFields.get(0).getText();
            surname = textFields.get(1).getText();
            try {
                workFrom = textFields.get(2).getText().isEmpty()
                        ? workFrom : Integer.parseInt(textFields.get(2).getText());
                workTo = textFields.get(3).getText().isEmpty()
                        ? workTo : Integer.parseInt(textFields.get(3).getText());
            } catch (NumberFormatException e) {
                repeatInput(event, "Error",
                        "Wrong number format", Alert.AlertType.ERROR);
            }
            if (!isInDayInterval(workFrom) || !isInDayInterval(workTo)
                    || workFrom > workTo) {
                workFrom = PracovniDoba.STANDARD_WORK_DAY.getBeginOfWorkDay();
                repeatInput(event, "Error",
                        "Unexisting hours", Alert.AlertType.ERROR);
            } else if (name.isEmpty() || surname.isEmpty()) {
                repeatInput(event, "Empty fields",
                        "You have one or more blank fields. Please, fill them.",
                        Alert.AlertType.INFORMATION);
            } else {
                therapist = new Terapeut(
                        new Person(name, surname),
                        new PracovniDoba(workFrom, workTo));
                showLabelWithWorkingHours();
            }
        });
    }

    private boolean isInDayInterval(final int hour) {
        return hour >= 0 && hour < 24;
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
        labelForWorkHours.setText(
                String.format("Therapist is working from %d to %d hours.", workFrom, workTo));
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
            final double topIndentation,
            final double leftIndentation,
            final String text) {
        TextField textField = new TextField(text);
        AnchorPane.setTopAnchor(textField, topIndentation);
        AnchorPane.setLeftAnchor(textField, leftIndentation);
        bgForDialog.getChildren().add(textField);
        return textField;
    }

    private List createTextFields() {
        List<TextField> fields = Arrays.asList(
                createTextField(50.0, 100.0, name),
                createTextField(90.0, 100.0, surname),
                createTextField(130.0, 100.0, workFrom.toString()),
                createTextField(170.0, 100.0, workTo.toString()));
        return fields;
    }

    private Label createLabel(
            final String text,
            final double topIndentation,
            final double leftIndentation) {
        Label label = new Label(text);
        AnchorPane.setTopAnchor(label, topIndentation);
        AnchorPane.setLeftAnchor(label, leftIndentation);
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
