package View;

import View.AView;
import algorithms.mazeGenerators.Maze;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class StartView extends AView implements Initializable {
    public Button NEW;
    public BorderPane borderPane;
    public ImageView background;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        background.setImage(new Image(getClass().getResourceAsStream("/Images/PikachuWalk.gif")));
        background.fitHeightProperty().bind(borderPane.heightProperty());
        background.fitWidthProperty().bind(borderPane.widthProperty());
    }

    @Override
    protected void init() {

    }

    /**
     * switch to new scene from type "New"
     *
     * @param actionEvent
     */
    public void createNewMaze(ActionEvent actionEvent) {
//        viewModel.switchScene((Stage) NEW.getScene().getWindow(), "New");
        switchScene((Stage) NEW.getScene().getWindow(), "New");
    }

    /**
     * switch to new scene from type "Load"
     *
//     * @param actionEvent
     */
    public void load(ActionEvent event) {
//        viewModel.switchScene(w, "Game");
        viewModel.loadMaze(NEW.getScene());
        event.consume();
    }


    /**
     * switch to new scene from type "Help"
     *
     * @param actionEvent
     */
    public void openHelp(ActionEvent actionEvent) {
        raiseStage("Help");
    }

    /**
     * switch to new scene from type "About"
     *
     * @param actionEvent
     */
    public void openAbout(ActionEvent actionEvent) {
        raiseStage("About");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null && arg instanceof File) {
            switchScene((Stage) NEW.getScene().getWindow(), "Game");
        }
    }
}
