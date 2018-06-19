package ViewModel;

import Model.IModel;
import View.AView;
import View.View;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
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
    public Scene currentscene;

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
//                    try {
////                        replace_music(new Media(ClassLoader.getSystemResource("music/pistols_drake.mp3").toURI().toString()));//finish music
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    }
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

    public void loadMaze() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("Mazes"));
        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "Maze files", "*.maze");
        fileChooser.getExtensionFilters().add(fileExtensions);
        File savefile = fileChooser.showOpenDialog(currentscene.getWindow());
        if (savefile == null)
            return;
        switchScene((Stage) currentscene.getWindow(), "Game");
        model.loadMaze(savefile);
    }

    public void saveMaze() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "Maze files", "*.maze");
        fileChooser.getExtensionFilters().add(fileExtensions);
        fileChooser.setInitialDirectory(new File("Mazes"));
        File savefile = fileChooser.showSaveDialog(currentscene.getWindow());
        if (savefile == null)
            return;
        model.saveMaze(savefile);
    }

    public void switchScene(Stage primaryStage, String sceneName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            try {
                System.out.println("hi 0"+View.class.getClass().getResourceAsStream("/src/View/StartView.fxml"));
            } catch (Exception e) {
            }
            try {
                System.out.println("1" + getClass().getResourceAsStream("../").toString());
            } catch (Exception e) {
                System.out.println("hi"+e.getCause());
            }
            try {
                System.out.println("2" + getClass().getResourceAsStream("../View").toString());
            } catch (Exception e) {
            }
            try {
                System.out.println("3" + getClass().getResourceAsStream(""));
                System.out.println("44"+getClass().getPackage().getClass().getPackage().getClass().getPackage());
            } catch (Exception e) {
            }
            try {
                System.out.println("4" + getClass().getResourceAsStream("StartView.fxml").toString());
            } catch (Exception e) {
            }
            try {
                System.out.println("5" + getClass().getResourceAsStream("View/StartView.fxml").toString());
            } catch (Exception e) {
            }
            Parent root = fxmlLoader.load(getClass().getResource("../View/" + sceneName + "View.fxml").openStream());
            Scene scene = new Scene(root, 800, 700);
            scene.getStylesheets().add(getClass().getResource("../View/" + sceneName + "Style.css").toExternalForm());
            primaryStage.setScene(scene);
            currentscene = scene;
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
        aboutStage.setResizable(true);
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
//        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.show();
    }

    public void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exit");
                alert.setHeaderText("Are you sure you want exit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    stopModel();
//                    primaryStage.close();
                    Platform.exit();
                } else {
                    windowEvent.consume();
                }
            }
        });
    }

    public void stopModel() {
        model.stopServers();
    }
}
