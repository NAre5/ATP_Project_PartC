package View.Start;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public class StartView implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    /**
     * switch to new scene from type "New"
     * @param actionEvent
     */
    public void createNewMaze(ActionEvent actionEvent) {
        showAlert("NEW");
    }

    /**
     * switch to new scene from type "Load"
     * @param actionEvent
     */
    public void loadMaze(ActionEvent actionEvent) {
        showAlert("LOAD");
    }

    /**
     * switch to new scene from type "Help"
     * @param actionEvent
     */
    public void openHelp(ActionEvent actionEvent) {
        showAlert("HELP");
    }

    /**
     * switch to new scene from type "About"
     * @param actionEvent
     */
    public void openAbout(ActionEvent actionEvent) {
        showAlert("ABOUT");
    }
}
