package ViewModel;

import Model.IModel;
import View.AView;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Aviadjo on 6/14/2017.
 */
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
            setChanged();
            notifyObservers();
        }
    }

    public void replace_music(Media media) {
        if (mediaPlayer!=null)
            mediaPlayer.dispose();
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }


    public void generateMaze(int width, int height) {
        model.generateMaze(width, height);
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return model.getMaze();
    }

    public Position getGoalPosition(){return model.getGoalPosition();}


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

    public Solution solveMaze() {
        return model.getMazeSolution();
    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    private void raise_NEW() {

    }

    public void saveMaze(String name) {
        model.saveMaze(name);
    }

    public Maze loadMaze(String name) {
        return model.loadMaze(name);
    }

    public void switchScene(Stage primaryStage, String sceneName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("../View/" + sceneName + "View.fxml").openStream());
            Scene scene = new Scene(root, 800, 700);
            scene.getStylesheets().add(getClass().getResource("../View/" +sceneName + "Style.css").toExternalForm());
            primaryStage.setScene(scene);
            AView view = fxmlLoader.getController();
            view.setViewModel(this);
            this.addObserver(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent setScene(String sceneName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource(sceneName + "View.fxml").openStream());
            return root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
