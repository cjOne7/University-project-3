package idats_yarosh_sem1_v1;

import therapy.Term;
import therapy.Therapist;
import therapy.WorkHours;
import therapy.GenerateTerms;
import therapy.Person;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import kolekce.*;
import sprava.*;

public class FXMLDocumentController implements Initializable {

    private static final int HEIGHT_FOR_HORIZONTAL_LABEL = 20;
    private static final int WIDTH_FOR_VERTICAL_LABEL = 40;
    private static final String FILE_NAME_BIN = "Terms.bin";
    private static final int RECT_WIDTH = 30;
    private static final int RECT_HEIGHT = 30;

    private final AnchorPane bgForDialog = new AnchorPane();
    private Therapist therapist = Therapist.EMPTY_THERAPIST;
    private String name = "";
    private String surname = "";
    private Integer workFrom = WorkHours.STANDARD_WORK_HOURS.getBeginOfWorkDay();
    private Integer workTo = WorkHours.STANDARD_WORK_HOURS.getEndOfWorkDay();
    private Rectangle[][] rects;
    private ObservableList<Term> terms = FXCollections.observableArrayList();
    private AbstrDoubleList<Term> listOfTerms = new AbstrDoubleList<>();
    private Term actualTerm = Term.EMPTY_TERM;
    private GenerateTerms generateTerms = new GenerateTerms();

    @FXML
    private ListView<Term> listView;
    @FXML
    private Label labelForWorkHours;
    @FXML
    private DatePicker datePickerFrom;
    @FXML
    private DatePicker datePickerTo;
    @FXML
    private Pane paneForDates;
    @FXML
    private TextField numberOfTerms;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datePickerFrom.setValue(LocalDate.now());
        datePickerTo.setValue(LocalDate.now().plusDays(7));
    }

    @FXML
    private void enterTerapeutData(ActionEvent event) {
        Dialog<ButtonType> inputDialog = new Dialog<>(); //create dialog

        bgForDialog.setPrefSize(300, 240);//set dialog size
        inputDialog.getDialogPane().setContent(bgForDialog);
        inputDialog.setTitle("Therapist name and working hours");

        createLabels(); //create a group of labels
        List<TextField> textFields = createTextFields();//create a group of text fields

        inputDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);//add to the dialog 2 buttons

        inputDialog.showAndWait().filter(bType -> bType == ButtonType.OK).ifPresent(t -> {
            name = textFields.get(0).getText();
            surname = textFields.get(1).getText();
            try {
                if (!textFields.get(2).getText().isEmpty()) {
                    workFrom = Integer.parseInt(textFields.get(2).getText());
                }
                if (!textFields.get(3).getText().isEmpty()) {
                    workTo = Integer.parseInt(textFields.get(3).getText());
                }
            } catch (NumberFormatException e) {
                repeatInput(event, "Error", "Wrong number format", Alert.AlertType.ERROR);
            }
            if (!isInDayInterval(workFrom) || !isInDayInterval(workTo) || workFrom > workTo) {
                workFrom = WorkHours.STANDARD_WORK_HOURS.getBeginOfWorkDay();
                repeatInput(event, "Error", "Unexisting hours", Alert.AlertType.ERROR);
            } else if (name.isEmpty() || surname.isEmpty()) {
                repeatInput(event, "Empty fields", "You have one or more blank fields. Please, fill them.",
                        Alert.AlertType.INFORMATION);
            } else {
                therapist = new Therapist(new Person(name, surname), new WorkHours(workFrom, workTo));
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

    private List<TextField> createTextFields() {
        return Arrays.asList(
                createTextField(50.0, 100.0, name),
                createTextField(90.0, 100.0, surname),
                createTextField(130.0, 100.0, workFrom.toString()),
                createTextField(170.0, 100.0, workTo.toString()));
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

    private Label createLabel(
            final String text,
            final int labelWidth,
            final int labelHeight,
            final int i, final int j,
            final boolean orientation) {
        Label label = new Label(text);
        if (orientation) {
            label.setPrefSize(labelWidth, HEIGHT_FOR_HORIZONTAL_LABEL);
            label.setTranslateX(labelWidth * i);
            label.setTranslateY(labelHeight * j - HEIGHT_FOR_HORIZONTAL_LABEL);
        } else {
            label.setPrefSize(WIDTH_FOR_VERTICAL_LABEL, labelHeight);
            label.setTranslateX(labelWidth * i - WIDTH_FOR_VERTICAL_LABEL);
            label.setTranslateY(labelHeight * j);
        }
        label.setAlignment(Pos.CENTER_LEFT);
        paneForDates.getChildren().add(label);
        return label;
    }

    private void createLabels() {
        createLabel("Name:", 55.0, 10.0);
        createLabel("Surname:", 95.0, 10.0);
        createLabel("Work from:", 135.0, 10.0);
        createLabel("Work to:", 175.0, 10.0);
    }

    private Rectangle createRect(
            final int widthOfRect,
            final int heightOfRect,
            final int i,
            final int j) {
        Rectangle r = new Rectangle(widthOfRect * i, heightOfRect * j, widthOfRect, heightOfRect);
        r.setFill(Color.GREY);
        r.setStroke(Color.BLACK);
        r.setStrokeWidth(2);
        paneForDates.getChildren().add(r);
        return r;
    }

    private void createShedule() {
        paneForDates.getChildren().clear();

        LocalDate todaysDate = datePickerFrom.getValue();
        LocalDate nextDate = datePickerTo.getValue();

//        int todaysDay = todaysDate.getDayOfMonth();
//        int numberOfMonth = todaysDate.getMonthValue();
//        int lengthOfMonth = todaysDate.lengthOfMonth();
        int rows = (int) Math.abs(todaysDate.toEpochDay() - nextDate.toEpochDay()) + 1;
        int columns = therapist.getWorkHours().getDurOfWorkDay();

//        int widthOfRect = 30;
//        int heightOfRect = 30;
        drawShedule(columns, rows, todaysDate);

//        int hour = therapist.getWorkHours().getBeginOfWorkDay();
//        rects = new Rectangle[columns][rows];
//        for (int i = 0; i < columns; i++) {
//            for (int j = 0; j < rows; j++) {
//                if (j == 0) {
//                    createLabel(hour++ + "h", widthOfRect, heightOfRect, i, j, true);
//                }
//                if (i == 0) {
//                    if (todaysDay > lengthOfMonth) {
//                        todaysDay = 1;
//                        numberOfMonth++;
//                        todaysDate = todaysDate.plusMonths(1);
//                        lengthOfMonth = todaysDate.lengthOfMonth();
//                    }
//                    createLabel(String.format("%02d.%02d", todaysDay++, numberOfMonth), widthOfRect, heightOfRect, i, j, false);
//                }
//                rects[i][j] = createRect(widthOfRect, heightOfRect, i, j);
//            }
//        }
    }

    private void drawShedule(final int columns, final int rows, LocalDate todaysDate) {
        paneForDates.getChildren().clear();
        int todaysDay = todaysDate.getDayOfMonth();
        int numberOfMonth = todaysDate.getMonthValue();
        int lengthOfMonth = todaysDate.lengthOfMonth();

        int hour = therapist.getWorkHours().getBeginOfWorkDay();
        rects = new Rectangle[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if (j == 0) {
                    createLabel(hour++ + "h", RECT_WIDTH, RECT_HEIGHT, i, j, true);
                }
                if (i == 0) {
                    if (todaysDay > lengthOfMonth) {
                        todaysDay = 1;
                        numberOfMonth++;
                        todaysDate = todaysDate.plusMonths(1);
                        lengthOfMonth = todaysDate.lengthOfMonth();
                    }
                    createLabel(String.format("%02d.%02d", todaysDay++, numberOfMonth), RECT_WIDTH, RECT_HEIGHT, i, j, false);
                }
                rects[i][j] = createRect(RECT_WIDTH, RECT_HEIGHT, i, j);
            }
        }
    }

    @FXML
    private void generateTerms(ActionEvent event) {
        int countOfTerms = 0;
        try {
            countOfTerms = Integer.parseInt(numberOfTerms.getText());
        } catch (NumberFormatException e) {
            callAlertWindow("Parsing error", "Wrong format number!", Alert.AlertType.ERROR);
            return;
        }
        createShedule();
        listView.getItems().clear();
        listOfTerms = generateTerms.generateTerms(countOfTerms, therapist.getWorkHours(), datePickerFrom, datePickerTo);
        refreshTerms();
        listView.setItems(terms);
        repaintRects();
    }

    private void repaintRects() {
        for (int i = 0; i < rects.length; i++) {
            for (int j = 0; j < rects[i].length; j++) {
                if (generateTerms.getIsBusy()[i][j]) {
                    rects[i][j].setFill(Color.YELLOWGREEN);
                }
            }
        }
    }

    private void refreshTerms() {
        Iterator<Term> it = listOfTerms.iterator();
        while (it.hasNext()) {
            terms.add(it.next());
        }
    }

    @FXML
    private void saveTerms(ActionEvent event) {
        Serializer.saveBinary(listOfTerms, FILE_NAME_BIN, generateTerms.getIsBusy());
    }

    @FXML
    private void loadTerms(ActionEvent event) {
        listView.getItems().clear();
        Serializer.loadBinary(listOfTerms, FILE_NAME_BIN, generateTerms);
        drawShedule(generateTerms.getIsBusy().length, generateTerms.getIsBusy()[0].length, datePickerFrom.getValue());
        refreshTerms();

        for (Rectangle[] rect : rects) {
            for (Rectangle rect1 : rect) {
                rect1.setFill(Color.GREY);
            }
        }
        repaintRects();
    }

    @FXML
    private void clearTerms(ActionEvent event) {
        listView.getItems().clear();
        terms.clear();
        listOfTerms.zrus();
        paneForDates.getChildren().clear();
    }

    @FXML
    private void findFirstTerm(ActionEvent event) {
        if (!terms.isEmpty()) {
            listView.getSelectionModel().selectFirst();
            actualTerm = listView.getSelectionModel().getSelectedItem();
            listOfTerms.zpristupniPrvni();
            while (listOfTerms.zpristupniAktualni() != actualTerm) {
                listOfTerms.zpristupniNaslednika();
            }
            System.out.println(actualTerm);
            System.out.println(listOfTerms.zpristupniAktualni());
        } else {
            callAlertWindow("Empty list", "You have empty list of terms", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void findNextTerm(ActionEvent event) {
        if (!listView.getSelectionModel().getSelectedItems().isEmpty()) {
            listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() + 1);
            actualTerm = listView.getSelectionModel().getSelectedItem();
            listOfTerms.zpristupniPrvni();
            while (listOfTerms.zpristupniAktualni() != actualTerm) {
                listOfTerms.zpristupniNaslednika();
            }
            System.out.println(actualTerm);
            System.out.println(listOfTerms.zpristupniAktualni());
        } else {
            callAlertWindow("Not selected item", "You didn't select any item from list.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void findPreviousTerm(ActionEvent event) {
        if (terms.isEmpty()) {
            callAlertWindow("Not selected item", "You didn't select any item from list.", Alert.AlertType.INFORMATION);
        }
        int selectedIndex = listView.getSelectionModel().getSelectedIndex() - 1;
        if (selectedIndex <= 0) {
            listView.getSelectionModel().selectFirst();
            actualTerm = listView.getSelectionModel().getSelectedItem();
            listOfTerms.zpristupniPosledni();
            Term tAct = listOfTerms.zpristupniAktualni();
            while (tAct != actualTerm) {
                listOfTerms.zpristupniPredchudce();
                System.out.println(tAct);
            }
            System.out.println(actualTerm);
            System.out.println(listOfTerms.zpristupniAktualni());
        } else {
            listView.getSelectionModel().select(selectedIndex);
            actualTerm = listView.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    private void findLastTerm(ActionEvent event) {
        if (!terms.isEmpty()) {
            listView.getSelectionModel().selectLast();
            actualTerm = listView.getSelectionModel().getSelectedItem();
            listOfTerms.zpristupniPosledni();
            while (listOfTerms.zpristupniAktualni() != actualTerm) {
                listOfTerms.zpristupniPredchudce();
            }
            System.out.println(actualTerm);
            System.out.println(listOfTerms.zpristupniAktualni());
        } else {
            callAlertWindow("Empty list", "You have empty list of terms", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void deleteFirstTerm(ActionEvent event) {

    }

    @FXML
    private void deleteActualTerm(ActionEvent event) {
        if (terms.contains(actualTerm)) {
            terms.remove(actualTerm);
//            actualTerm = listOfTerms.
        } else {
            callAlertWindow("The actual item is not selected", "You didn't have select actual item from list", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void deleteNextTerm(ActionEvent event) {

    }

    @FXML
    private void deletePreviousTerm(ActionEvent event) {

    }

    @FXML
    private void deleteLastTerm(ActionEvent event) {

    }
}
