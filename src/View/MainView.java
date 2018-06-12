package View;

import Server.Server;
import View.AView;
import View.MazeDisplayer;
import algorithms.mazeGenerators.Maze;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MainView extends AView implements Initializable {

    public MazeDisplayer mazeDisplayer;

    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            displayMaze(viewModel.getMaze());
        }
    }

    public void displayMaze(int[][] maze) {
        mazeDisplayer.setMaze(maze);
        int characterPositionRow = viewModel.getCharacterPositionRow();
        int characterPositionColumn = viewModel.getCharacterPositionColumn();
        mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //add disable button
    public void solveMaze(ActionEvent actionEvent) {
        try {
            mazeDisplayer.drawSolution(viewModel.solveMaze());
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public void KeyPressed(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
    }

    @Override
    protected void init() {

    }

    public void saveMaze(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Save maze");
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Pane pane = new Pane();
            VBox vb = new VBox();
            Label lbl = new Label("Enter maze name:");
            Button btn = new Button("save");
            TextField txt = new TextField();
            vb.getChildren().addAll(lbl, txt, btn);
            pane.getChildren().addAll(vb);
            Scene scene = new Scene(pane, 200, 150);
            stage.setScene(scene);
//            primaryStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
            btn.setOnAction(ev->{
                String name = txt.getText();
                if (name == null || name.trim().length()==0)
                {
                    showAlert("Name file must contain at least one character");
                    ev.consume();
                }
                if(name.matches(".*[/:*?<>|\"].*")||name.contains("\\"))
                {
                   showAlert("File name can not contain /:*?<>|\"\\");
                   ev.consume();
                }
                try
                {
                    viewModel.saveMaze(name);
                }
                catch (IllegalArgumentException e)
                {
                    showAlert("File name already exists");
                    ev.consume();
                }
            });

        } catch (Exception e) {

        }
    }

    public void loadMaze(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Load maze");
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Pane pane = new Pane();
            VBox vb = new VBox();
            Label lbl = new Label("Enter maze name:");
            Button btn = new Button("Load");
            TextField txt = new TextField();
            vb.getChildren().addAll(lbl, txt, btn);
            pane.getChildren().addAll(vb);
            Scene scene = new Scene(pane, 200, 150);
            stage.setScene(scene);
//            primaryStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
            btn.setOnAction(ev->{
                String name = txt.getText();
                if (name == null || name.trim().length()==0)
                {
                    showAlert("Name file must contain at least one character");
                    ev.consume();
                }
                if(name.matches(".*[/:*?<>|\"].*")||name.contains("\\"))
                {
                    showAlert("File name can not contain /:*?<>|\"\\");
                    ev.consume();
                }
                try
                {
                    Maze loadMaze = viewModel.loadMaze(name);
                    mazeDisplayer.setMaze(loadMaze.getMaze());
                }
                catch (IllegalArgumentException e)
                {
                    showAlert("File name does not exists");
                    ev.consume();
                }
            });

        } catch (Exception e) {

        }
    }

    public void StartMaze(ActionEvent actionEvent) {
        Stage aboutStage = new Stage();
//        aboutStage.setAlwaysOnTop(true);
//        aboutStage.setResizable(false);
        aboutStage.setTitle("New");

        Parent root=null;
        try
        {
            //change MyView.fxml to help.fxml after designed
            root = FXMLLoader.load(getClass().getResource("NewView.fxml"));
        }
        catch(IOException e) {
            showAlert("Exception!");
        }
        Scene scene = new Scene(root,700,400);
        aboutStage.setScene(scene);
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.show();
    }

    public void About(ActionEvent actionEvent) {
        Stage aboutStage = new Stage();
        aboutStage.setAlwaysOnTop(true);
        aboutStage.setResizable(false);
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
}
