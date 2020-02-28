package idats_yarosh_sem1_v1;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class IDATS_Yarosh_Sem1_v1 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);
        stage.setOnCloseRequest((event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exiting");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure that you want to close app?");
            Optional<ButtonType> op = alert.showAndWait();
            if (op.get().equals(ButtonType.OK)) {
                Platform.exit();
                System.exit(0);
            } else {
                event.consume();
            }
        });
        stage.setResizable(false);
        stage.setTitle("The shedule of therapy");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
