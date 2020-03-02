package idats_yarosh_sem1_v1;

import colection.AbstrDoubleList;
import therapy.Term;
import therapistData.Therapist;
import therapy.GenerateTerms;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sprava.*;
import therapistData.TherapistInputDialog;

public class FXMLDocumentController implements Initializable {

    private static final int HEIGHT_FOR_HORIZONTAL_LABEL = 20;
    private static final int WIDTH_FOR_VERTICAL_LABEL = 40;
    private static final String FILE_NAME_BIN = "Terms.bin";
    private static final int RECT_WIDTH = 30;
    private static final int RECT_HEIGHT = 30;

    private Therapist therapist = Therapist.EMPTY_THERAPIST;
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
        numberOfTerms.setText("10");
    }

    @FXML
    private void enterTerapeutData(ActionEvent event) {
        TherapistInputDialog therapistInputDialog = new TherapistInputDialog();
        therapistInputDialog.workWithTherapistData(labelForWorkHours);
        therapist = therapistInputDialog.getTherapist();
    }

    public static Alert callAlertWindow(
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
        int rows = (int) Math.abs(datePickerFrom.getValue().toEpochDay() - datePickerTo.getValue().toEpochDay()) + 1;
        int columns = therapist.getWorkHours().getDurOfWorkDay();
        drawShedule(columns, rows, datePickerFrom.getValue());
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
        int countOfTerms;
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
        } else {
            callAlertWindow("Empty list", "You have empty list of terms.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void findNextTerm(ActionEvent event) {
        if (!listView.getSelectionModel().getSelectedItems().isEmpty()) {
            listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() + 1);
            actualTerm = listView.getSelectionModel().getSelectedItem();
            listOfTerms.zpristupniNaslednika();
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
            listOfTerms.zpristupniPrvni();
        } else {
            listView.getSelectionModel().select(selectedIndex);
            actualTerm = listView.getSelectionModel().getSelectedItem();
            listOfTerms.zpristupniPredchudce();
        }
    }

    @FXML
    private void findLastTerm(ActionEvent event) {
        if (!listView.getSelectionModel().getSelectedItems().isEmpty()) {
            listView.getSelectionModel().selectLast();
            actualTerm = listView.getSelectionModel().getSelectedItem();
            listOfTerms.zpristupniPosledni();
        } else {
            callAlertWindow("Empty list", "You have empty list of terms.", Alert.AlertType.INFORMATION);
        }
    }

    private void repaintDeletedRects(final Term deletedTerm) {
        long row = Math.abs(deletedTerm.getStart().toLocalDate().toEpochDay() - datePickerFrom.getValue().toEpochDay());
        int column = Math.abs(deletedTerm.getStart().getHour() - therapist.getWorkHours().getBeginOfWorkDay());
        rects[column][(int) row].setFill(Color.GREY);
        rects[column + 1][(int) row].setFill(Color.GREY);
        if (deletedTerm.getDurOfTerm().getDurOfTherapy() == 4) {
            rects[column + 2][(int) row].setFill(Color.GREY);
            rects[column + 3][(int) row].setFill(Color.GREY);
        }
    }

    @FXML
    private void deleteFirstTerm(ActionEvent event) {
        if (!terms.isEmpty()) {
            Term deletedTerm = terms.remove(0);
            repaintDeletedRects(deletedTerm);
            if (terms.isEmpty()) {
                return;
            }
            actualTerm = terms.get(0);
            listOfTerms.odeberPrvni();
        } else {
            callAlertWindow("Empty list", "You have empty list of terms.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void deleteActualTerm(ActionEvent event) {
//        callAlertWindow("The actual item is not selected", "You didn't have select actual item from list", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void deleteNextTerm(ActionEvent event) {

    }

    @FXML
    private void deletePreviousTerm(ActionEvent event) {

    }

    @FXML
    private void deleteLastTerm(ActionEvent event) {
        if (!terms.isEmpty()) {
            Term deletedTerm = terms.remove(terms.size() - 1);
            repaintDeletedRects(deletedTerm);
            if (terms.isEmpty()) {
                return;
            }
            actualTerm = terms.get(terms.size() - 1);
            listOfTerms.odeberPosledni();
        } else {
            callAlertWindow("Empty list", "You have empty list of terms.", Alert.AlertType.INFORMATION);
        }
    }
}
