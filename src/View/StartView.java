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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("hi i am here");
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
        viewModel.switchScene((Stage) NEW.getScene().getWindow(), "New");
    }

    /**
     * switch to new scene from type "Load"
     *
//     * @param actionEvent
     */
    public void load(ActionEvent event) {
//        viewModel.switchScene(w, "Game");
        viewModel.loadMaze();
        event.consume();
    }


    /**
     * switch to new scene from type "Help"
     *
     * @param actionEvent
     */
    public void openHelp(ActionEvent actionEvent) {
        viewModel.raiseStage("Help");
    }

    /**
     * switch to new scene from type "About"
     *
     * @param actionEvent
     */
    public void openAbout(ActionEvent actionEvent) {
        viewModel.raiseStage("About");
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
