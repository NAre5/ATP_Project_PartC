package View;

import View.AView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class StartView extends AView implements Initializable {
    public Button NEW;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    protected void init() {

    }

    /**
     * switch to new scene from type "New"
     * @param actionEvent
     */
    public void createNewMaze(ActionEvent actionEvent) {
        switchScene((Stage)NEW.getScene().getWindow(),"New");
//        showAlert("NEW");
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
        Stage aboutStage = new Stage();
//        aboutStage.setAlwaysOnTop(true);
//        aboutStage.setResizable(false);
        aboutStage.setTitle("About");

        Parent root=null;
        try
        {
            //change MyView.fxml to help.fxml after designed
            root = FXMLLoader.load(getClass().getResource("About.fxml"));
        }
        catch(IOException e) {
            showAlert("Exception!");
        }
        Scene scene = new Scene(root,700,400);
        aboutStage.setScene(scene);
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.show();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
