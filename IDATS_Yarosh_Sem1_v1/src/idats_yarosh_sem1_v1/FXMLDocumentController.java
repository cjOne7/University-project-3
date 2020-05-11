package idats_yarosh_sem1_v1;

import therapy.Serializer;
import collection.AbstrDoubleList;
import collection.CollectionException;
import therapy.Term;
import therapistData.Therapist;
import therapy.GenerateTerms;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
import therapistData.TherapistInputDialog;
import therapy.DurOfTherapy;

public class FXMLDocumentController implements Initializable {

    private static final int HEIGHT_FOR_HORIZONTAL_LABEL = 20;
    private static final int WIDTH_FOR_VERTICAL_LABEL = 40;
    private static final String FILE_NAME_BIN = "Terms.bin";
    private static final int RECT_WIDTH = 26;
    private static final int RECT_HEIGHT = 26;

    private Therapist therapist = Therapist.EMPTY_THERAPIST;
    private Rectangle[][] rects;
    private final ObservableList<Term> terms = FXCollections.observableArrayList();
    private AbstrDoubleList<Term> listOfTerms = new AbstrDoubleList<>();
    private Term actualTerm = Term.EMPTY_TERM;
    private final GenerateTerms generateTerms = new GenerateTerms();

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
        listView.setItems(terms);
    }

    @FXML
    private void enterTerapeutData(ActionEvent event) {
        final TherapistInputDialog therapistInputDialog = new TherapistInputDialog();
        therapistInputDialog.workWithTherapistData(labelForWorkHours);
        therapist = therapistInputDialog.getTherapist();
        if (!therapist.getPerson().getName().equals("") && !therapist.getPerson().getSurname().equals("")) {
            labelForWorkHours.setVisible(true);
        }
    }

    public static Alert callAlertWindow(
            final String titleText,
            final String contextText,
            final Alert.AlertType alertType) {
        final Alert alert = new Alert(alertType);
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
            final int i,
            final int j,
            final boolean orientation) {
        final Label label = new Label(text);
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
        final Rectangle r = new Rectangle(widthOfRect * i, heightOfRect * j, widthOfRect, heightOfRect);
        r.setFill(Color.GREY);
        r.setStroke(Color.BLACK);
        r.setStrokeWidth(2);
        paneForDates.getChildren().add(r);
        return r;
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
        int rows = (int) Math.abs(datePickerFrom.getValue().toEpochDay() - datePickerTo.getValue().toEpochDay()) + 1;
        int columns = therapist.getWorkHours().getDurOfWorkDay();
        drawShedule(columns, rows, datePickerFrom.getValue());
        listView.getItems().clear();
        listOfTerms = generateTerms.generateTerms(
                countOfTerms,
                therapist.getWorkHours(),
                datePickerFrom.getValue(),
                datePickerTo.getValue());
        refreshTerms();
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
        final Iterator<Term> it = listOfTerms.iterator();
        while (it.hasNext()) {
            terms.add(it.next());
        }
    }

    @FXML
    private void saveTerms(ActionEvent event) {
        if (!terms.isEmpty()) {
            Serializer.saveBinary(listOfTerms, FILE_NAME_BIN, generateTerms.getIsBusy());
        } else {
            callAlertWindow("Empty list", "You have empty list of terms.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void loadTerms(ActionEvent event) {
        listView.getItems().clear();
        Serializer.loadBinary(listOfTerms, FILE_NAME_BIN, generateTerms);
        if (!listOfTerms.isEmpty()) {
            drawShedule(generateTerms.getIsBusy().length, generateTerms.getIsBusy()[0].length, datePickerFrom.getValue());
            refreshTerms();
            repaintRects();
        }
    }

    @FXML
    private void clearTerms(ActionEvent event) {
        listView.getItems().clear();
        terms.clear();
        listOfTerms.clear();
        clearRects();
    }

    @FXML
    private void findFirstTerm(ActionEvent event) {
        if (terms.isEmpty()) {
            callAlertWindow("Empty list", "You have empty list of terms.", Alert.AlertType.INFORMATION);
        } else {
            listView.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void findNextTerm(ActionEvent event) {
        if (terms.isEmpty()) {
            callAlertWindow("Empty list", "You have empty list of terms.", Alert.AlertType.INFORMATION);
        } else {
            listView.getSelectionModel().selectNext();
        }
    }

    @FXML
    private void findPreviousTerm(ActionEvent event) {
        if (terms.isEmpty()) {
            callAlertWindow("Empty list", "You have empty list of terms.", Alert.AlertType.INFORMATION);
        } else {
            listView.getSelectionModel().selectPrevious();
        }
    }

    @FXML
    private void findLastTerm(ActionEvent event) {
        if (terms.isEmpty()) {
            callAlertWindow("Empty list", "You have empty list of terms.", Alert.AlertType.INFORMATION);
        } else {
            listView.getSelectionModel().selectLast();
        }
    }

    private void repaintRects(
            final int column,
            final long row,
            final int durOfTherapy,
            final Color color) {
        rects[column][(int) row].setFill(color);
        rects[column + 1][(int) row].setFill(color);
        if (durOfTherapy == DurOfTherapy.LONG.getDurOfTherapy()) {
            rects[column + 2][(int) row].setFill(color);
            rects[column + 3][(int) row].setFill(color);
        }
    }

    private void clearRects() {
        if (terms.isEmpty()) {
            paneForDates.getChildren().clear();
        }
    }

    @FXML
    private void deleteFirstTerm(ActionEvent event) {
        if (!terms.isEmpty()) {
            final Term deletedTerm = terms.remove(0);
            repaintRects(
                    getIndexColumn(deletedTerm),
                    getIndexRow(deletedTerm),
                    deletedTerm.getDurOfTerm().getDurOfTherapy(),
                    Color.GREY);
            rechangeStates(
                    getIndexColumn(deletedTerm), 
                    (int) getIndexRow(deletedTerm), 
                    deletedTerm.getDurOfTerm().getDurOfTherapy(), 
                    false);
            listOfTerms.deleteFirst();
            clearRects();
        } else {
            callAlertWindow("Empty list", 
                    "You have empty list of terms.", 
                    Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void deleteActualTerm(ActionEvent event) {
        rmActualElement();
        clearRects();
    }

    @FXML
    private void deleteNextTerm(ActionEvent event) {
        if (!terms.isEmpty()) {
            final Term lastTerm = terms.get(terms.size() - 1);
            if (listView.getSelectionModel().getSelectedItem() != null) {
                if (!listView.getSelectionModel().getSelectedItem().equals(lastTerm)) {
                    listView.getSelectionModel().selectNext();
                    rmActualElement();
                } else {
                    callAlertWindow("Next element is absent",
                            "You can't delete next element from list.",
                            Alert.AlertType.INFORMATION);
                }
            } else {
                callAlertWindow("The actual item is not selected",
                        "You didn't select actual item from list",
                        Alert.AlertType.INFORMATION);
            }
        } else {
            callAlertWindow("Empty list",
                    "You have empty list of terms.",
                    Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void deletePreviousTerm(ActionEvent event) {
        if (!terms.isEmpty()) {
            final Term firstTerm = terms.get(0);
            if (listView.getSelectionModel().getSelectedItem() != null) {
                if (!listView.getSelectionModel().getSelectedItem().equals(firstTerm)) {
                    listView.getSelectionModel().selectPrevious();
                    rmActualElement();
                    if (terms.size() != 1) {
                        listView.getSelectionModel().selectNext();
                    }
                } else {
                    callAlertWindow("Previous element is absent",
                            "You can't delete previous element from list.",
                            Alert.AlertType.INFORMATION);
                }
            } else {
                callAlertWindow("The actual item is not selected",
                        "You didn't select actual item from list",
                        Alert.AlertType.INFORMATION);
            }
        } else {
            callAlertWindow("Empty list",
                    "You have empty list of terms.",
                    Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void deleteLastTerm(ActionEvent event) {
        if (!terms.isEmpty()) {
            final Term deletedTerm = terms.remove(terms.size() - 1);
            repaintRects(
                    getIndexColumn(deletedTerm),
                    getIndexRow(deletedTerm),
                    deletedTerm.getDurOfTerm().getDurOfTherapy(),
                    Color.GREY);
            rechangeStates(
                    getIndexColumn(deletedTerm), 
                    (int) getIndexRow(deletedTerm), 
                    deletedTerm.getDurOfTerm().getDurOfTherapy(), 
                    false);
            listOfTerms.deleteLast();
            clearRects();
        } else {
            callAlertWindow("Empty list", "You have empty list of terms.", Alert.AlertType.INFORMATION);
        }
    }

    private void rmActualElement() {
        actualTerm = listView.getSelectionModel().getSelectedItem();
        if (actualTerm != null) {
            adjustActualElement(actualTerm);
            terms.remove(actualTerm);
            repaintRects(
                    getIndexColumn(actualTerm),
                    getIndexRow(actualTerm),
                    actualTerm.getDurOfTerm().getDurOfTherapy(),
                    Color.GREY);
            rechangeStates(
                    getIndexColumn(actualTerm), 
                    (int) getIndexRow(actualTerm), 
                    actualTerm.getDurOfTerm().getDurOfTherapy(), 
                    false);
            listOfTerms.deleteActual();
        } else {
            callAlertWindow("The actual item is not selected",
                    "You didn't select actual item from list",
                    Alert.AlertType.INFORMATION);
        }
    }

    private long getIndexRow(final Term term) {
        return Math.abs(term.getStart().toLocalDate().toEpochDay() - datePickerFrom.getValue().toEpochDay());
    }

    private int getIndexColumn(final Term term) {
        return Math.abs(term.getStart().getHour() - therapist.getWorkHours().getBeginOfWorkDay());
    }

    public void adjustActualElement(final Term element) {
        try {
            Term term = listOfTerms.getFirst();
            Iterator<Term> it = listOfTerms.iterator();
            while (it.hasNext()) {
                if (term.equals(element)) {
                    break;
                }
                term = listOfTerms.getNext();
            }
        } catch (CollectionException ex) {
            callAlertWindow("Actual element is null",
                    "Your actual element has null value.",
                    Alert.AlertType.ERROR);
        } catch (NoSuchElementException ex) {
            callAlertWindow("Element is absent",
                    "You have null element.",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void findAndInsert(ActionEvent event) {
        if (terms.isEmpty()) {
            numberOfTerms.setText("0");
            generateTerms(event);
        }
        DurOfTherapy therapy = generateTerms.getRandomDurOfTherapy();
        int durOfTherapy = therapy.getDurOfTherapy();

        final int lengthC = generateTerms.getIsBusy()[0].length;
        final int lengthR = generateTerms.getIsBusy().length;
        loop:
        for (int i = 0; i < lengthC; i++) {
            for (int j = 0; j < lengthR; j += 2) {
                if (generateTerms.isAnyFreeSpace()) {
                    callAlertWindow("Full list", "You can't add any term!", Alert.AlertType.INFORMATION);
                    return;
                }
                final boolean state = generateTerms.getIsBusy()[j][i];
                if (state == false) {
                    if (j + 2 == lengthR) {
                        therapy = DurOfTherapy.SHORT;
                        durOfTherapy = therapy.getDurOfTherapy();
                    } else if (generateTerms.getIsBusy()[j + 2][i] && durOfTherapy == 4) {
                        therapy = DurOfTherapy.SHORT;
                        durOfTherapy = therapy.getDurOfTherapy();
                    }
                    final int hour = therapist.getWorkHours().getBeginOfWorkDay();
                    final LocalDateTime timeFrom = LocalDateTime.of(
                            LocalDate.now().plusDays(i).getYear(),
                            LocalDate.now().plusDays(i).getMonth(),
                            LocalDate.now().plusDays(i).getDayOfMonth(),
                            hour + j, 0);
                    final LocalDateTime timeTo = LocalDateTime.of(
                            LocalDate.now().plusDays(i).getYear(),
                            LocalDate.now().plusDays(i).getMonth(),
                            LocalDate.now().plusDays(i).getDayOfMonth(),
                            hour + j + durOfTherapy, 0);
                    final Term term = new Term(generateTerms.getRandomTherapy(), therapy, timeFrom, timeTo);
                    terms.add(term);
                    listOfTerms.addFirst(term);
                    repaintRects(j, i, durOfTherapy, Color.YELLOWGREEN);
                    rechangeStates(j, i, durOfTherapy, true);
                    break loop;
                }
            }
        }
    }

    private void rechangeStates(
            final int j,
            final int i,
            final int durOfTherapy,
            final boolean state) {
        generateTerms.getIsBusy()[j][i] = state;
        generateTerms.getIsBusy()[j + 1][i] = state;
        if (durOfTherapy == DurOfTherapy.LONG.getDurOfTherapy()) {
            generateTerms.getIsBusy()[j + 2][i] = state;
            generateTerms.getIsBusy()[j + 3][i] = state;
        }
    }

    @FXML
    private void sortCollection(ActionEvent event) {
        listView.getItems().clear();
        listOfTerms.sortCollection(listOfTerms);
        refreshTerms();
    }
}
