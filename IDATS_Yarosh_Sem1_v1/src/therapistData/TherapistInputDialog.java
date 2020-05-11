package therapistData;

import idats_yarosh_sem1_v1.FXMLDocumentController;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public final class TherapistInputDialog {

    private final AnchorPane bgForDialog = new AnchorPane();
    private static String name = "";
    private static String surname = "";
    private static Integer workFrom = WorkHours.STANDARD_WORK_HOURS.getBeginOfWorkDay();
    private static Integer workTo = WorkHours.STANDARD_WORK_HOURS.getEndOfWorkDay();
    private static Therapist therapist = Therapist.EMPTY_THERAPIST;

    public void workWithTherapistData(final Label label) {
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
                repeatInput(label, "Error", "Wrong number format", Alert.AlertType.ERROR);
            }
            if (!isInDayInterval(workFrom) || !isInDayInterval(workTo) || workFrom > workTo) {
                workFrom = WorkHours.STANDARD_WORK_HOURS.getBeginOfWorkDay();
                repeatInput(label, "Error", "Unexisting hours", Alert.AlertType.ERROR);
            } else if (name.isEmpty() || surname.isEmpty()) {
                repeatInput(label, "Empty fields", "You have one or more blank fields. Please, fill them.", Alert.AlertType.INFORMATION);
            } else {
                therapist = new Therapist(new Person(name, surname), new WorkHours(workFrom, workTo));
            }
            label.setText(String.format("Therapist is working from %d to %d hours.",
                    therapist.getWorkHours().getBeginOfWorkDay(), therapist.getWorkHours().getEndOfWorkDay()));
        });
    }

    private boolean isInDayInterval(final int hour) {
        return hour >= 0 && hour < 24;
    }

    private void repeatInput(
            final Label label,
            final String titleText,
            final String contextText,
            final Alert.AlertType alertType) {
        FXMLDocumentController.callAlertWindow(titleText, contextText, alertType);
        workWithTherapistData(label);
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

    private void createLabels() {
        createLabel("Name:", 55.0, 10.0);
        createLabel("Surname:", 95.0, 10.0);
        createLabel("Work from:", 135.0, 10.0);
        createLabel("Work to:", 175.0, 10.0);
    }

    public Therapist getTherapist() {
        return therapist;
    }
}
