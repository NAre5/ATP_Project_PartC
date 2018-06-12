package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Aviadjo on 6/14/2017.
 */
public class ViewModel extends Observable implements Observer {

    public MediaPlayer mediaPlayer;
    public StringProperty characterPositionRow = new SimpleStringProperty("1"); //For Binding
    public StringProperty characterPositionColumn = new SimpleStringProperty("1"); //For Binding
    private IModel model;
    private int characterPositionRowIndex;
    private int characterPositionColumnIndex;

    private Scene scene;

    public ViewModel(IModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            //arg is Maze
            characterPositionRowIndex = model.getCharacterPositionRow();
            characterPositionRow.set(characterPositionRowIndex + "");
            characterPositionColumnIndex = model.getCharacterPositionColumn();
            characterPositionColumn.set(characterPositionColumnIndex + "");
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
//        mediaPlayer.dispose();
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }


    public int[][] generateMaze(int width, int height) {
        model.generateMaze(width, height);
        return model.getMaze();
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

    public Solution solveMaze() {
        return model.getMazeSolution();
    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    private void raise_NEW(){

    }

    public void saveMaze(String name) {
        model.saveMaze(name);
    }

    public Maze loadMaze(String name) {
        return model.loadMaze(name);
    }
    public Parent setScene(String sceneName)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource( sceneName + "View.fxml").openStream());
            return root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
