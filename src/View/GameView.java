package View;

import Server.Server;
import View.AView;
import View.MazeDisplayer;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
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

public class GameView extends AView implements Initializable {

    public MazeDisplayer mazeDisplayer;

    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            if (arg.toString().equals("maze"))
                displayMaze(viewModel.getMaze(),viewModel.getGoalPosition());
            else if (arg.toString().equals("solution"))
                mazeDisplayer.drawSolution(viewModel.getSolution());

        }
    }

    public void displayMaze(int[][] maze, Position goalPosition) {
        mazeDisplayer.setMaze(maze,goalPosition);
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
            viewModel.solveMaze();
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
//            Parent root = fxmlLoader.load(getClass().getResource("AboutView.fxml").openStream());
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
                    stage.close();
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
//            Parent root = fxmlLoader.load(getClass().getResource("AboutView.fxml").openStream());
            Pane pane = new Pane();
            VBox vb = new VBox();
            Label lbl = new Label("Enter maze name:");
            Button btn = new Button("Load");
            TextField txt = new TextField();
            vb.getChildren().addAll(lbl, txt, btn);
            pane.getChildren().addAll(vb);
            Scene scene = new Scene(pane, 300, 150);
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
                    viewModel.loadMaze(name);
                    stage.close();
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
        viewModel.switchScene((Stage)mazeDisplayer.getScene().getWindow(),"New");
    }

    public void About(ActionEvent actionEvent) {
        viewModel.raiseStage("About");
    }

    public void openHelp(ActionEvent actionEvent) {
        viewModel.raiseStage("Help");
    }

    public void openProperties(ActionEvent actionEvent) {
        viewModel.raiseStage("Properties");
    }
}
