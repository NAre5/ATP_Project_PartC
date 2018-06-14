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
        viewModel.switchScene((Stage) NEW.getScene().getWindow(), "New");
    }

    /**
     * switch to new scene from type "Load"
     *
//     * @param actionEvent
     */
//    public void loadMaze(ActionEvent actionEvent) {
//        try {
//            Stage stage = new Stage();
//            stage.setTitle("Load maze");
//            Pane pane = new Pane();
//            VBox vb = new VBox();
//            Label lbl = new Label("Enter maze name:");
//            Button btn = new Button("Load");
//            TextField txt = new TextField();
//            vb.getChildren().addAll(lbl, txt, btn);
//            pane.getChildren().addAll(vb);
//            Scene scene = new Scene(pane, 300, 150);
//            stage.setScene(scene);
////            primaryStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
//            stage.show();
//            btn.setOnAction(ev->{
//                String name = txt.getText();
//                if (name == null || name.trim().length()==0)
//                {
//                    showAlert("Name file must contain at least one character");
//                    ev.consume();
//                }
//                if(name.matches(".*[/:*?<>|\"].*")||name.contains("\\"))
//                {
//                    showAlert("File name can not contain /:*?<>|\"\\");
//                    ev.consume();
//                }
//                try
//                {
//                    viewModel.switchScene((Stage)NEW.getScene().getWindow(),"Game");
//                    viewModel.loadMaze(name);
//                    stage.close();
//
//                }
//                catch (IllegalArgumentException e)
//                {
//                    viewModel.switchScene((Stage)NEW.getScene().getWindow(),"Start");
//                    showAlert("File name does not exists");
//                    ev.consume();
//                }
//            });
//
//        } catch (Exception e) {
//
//        }
//    }
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
