package ViewModel;

import Model.IModel;
import View.AView;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;


public class ViewModel extends Observable implements Observer {

    public MediaPlayer mediaPlayer;
    private IModel model;
    private int characterPositionRowIndex;
    private int characterPositionColumnIndex;

    public IntegerProperty maze_rows_size;
    public IntegerProperty maze_columns_size;

    public ViewModel(IModel model) {
        this.model = model;
        maze_rows_size = new SimpleIntegerProperty();
        maze_columns_size = new SimpleIntegerProperty();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            //arg is Maze
            if (arg.toString().equals("maze")) {
                characterPositionRowIndex = model.getCharacterPositionRow();
                characterPositionColumnIndex = model.getCharacterPositionColumn();
                if (model.getGoalPosition().equals(new Position(characterPositionRowIndex, characterPositionColumnIndex))) {
                    try {
                        replace_music(new Media(ClassLoader.getSystemResource("music/pistols_drake.mp3").toURI().toString()));//finish music
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    showAlert("finished!!!");
                }

            }
            setChanged();
            notifyObservers(arg);
        }
    }

    public void replace_music(Media media) {
        if (mediaPlayer != null)
            mediaPlayer.dispose();
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setAutoPlay(true);
    }


    public void generateMaze(int width, int height) {
        model.generateMaze(width, height);
    }

    public Position getGoalPosition() {
        return model.getGoalPosition();
    }

    public Position getStartPosition() {
        return model.getStartPosition();
    }


    public void moveCharacter(KeyCode movement) {
        model.moveCharacter(movement);
    }

    public int[][] getMaze() {
        return model.getMaze();
    }

    public int getCharacterPositionRow() {
        return characterPositionRowIndex;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumnIndex;
    }

    public void solveMaze() {
        model.generateSolution();
    }

    public List<Pair<Integer, Integer>> getSolution() {
        return model.getSolution();
    }


    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    private void raise_NEW() {

    }

//    public void saveMaze(String name) {
//        model.saveMaze(name);
//    }

    public void loadMaze(File name) {
        model.loadMaze(name);
    }

    public void saveMaze(File file)
    {
        model.saveMaze(file);
    }
    public void switchScene(Stage primaryStage, String sceneName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("../View/" + sceneName + "View.fxml").openStream());
            Scene scene = new Scene(root, 800, 700);
            scene.getStylesheets().add(getClass().getResource("../View/" + sceneName + "Style.css").toExternalForm());
            primaryStage.setScene(scene);
            AView view = fxmlLoader.getController();
            view.setViewModel(this);
            this.addObserver(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void raiseStage(String sceneName) {
        Stage aboutStage = new Stage();
        aboutStage.setAlwaysOnTop(true);
        aboutStage.setResizable(false);
        aboutStage.setTitle(sceneName);

        Parent root = null;
        FXMLLoader fxmlLoader = null;
        try {
            //change MyView.fxml to help.fxml after designed
            fxmlLoader = new FXMLLoader();
            root = fxmlLoader.load(getClass().getResource("../View/" + sceneName + "View.fxml").openStream());
        } catch (IOException e) {
            showAlert("Exception!");
        }
        Scene scene = new Scene(root, 600, 650);
        aboutStage.setScene(scene);
        AView view = fxmlLoader.getController();
        view.setViewModel(this);
        this.addObserver(view);
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.show();
    }

    public void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    model.stopServers();
                    primaryStage.close();
                    // ... user chose OK
                    // Close program
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });
    }
}
