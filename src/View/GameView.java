package View;

import algorithms.mazeGenerators.Position;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

public class GameView extends AView implements Initializable {

    public MazeDisplayer mazeDisplayer;
    public Button Solve;
    public MenuBar menu;
    public VBox vb;
    boolean controlPressed = false;
    private double lastX;
    private double lastY;
    public BorderPane borderPane;

    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            if (arg.toString().equals("maze"))
                displayMaze(viewModel.getMaze(), viewModel.getGoalPosition());
            else if (arg.toString().equals("solution"))
                mazeDisplayer.drawSolution(viewModel.getSolution());

        }
    }

    public void displayMaze(int[][] maze, Position goalPosition) {
        mazeDisplayer.setMaze(maze, goalPosition);
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
        if (keyEvent.getCode() == KeyCode.CONTROL)
            controlPressed = true;
        else
            viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
    }

    @Override
    protected void init() {
        setResizeEvent(mazeDisplayer.getScene());
    }


    public void load(ActionEvent event) {
        viewModel.loadMaze();
        event.consume();
    }


    public void saveMaze(ActionEvent event) {
        viewModel.saveMaze();
    }


    public void StartMaze(ActionEvent actionEvent) {
        viewModel.switchScene((Stage) mazeDisplayer.getScene().getWindow(), "New");
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

    public void scrollEvent(ScrollEvent scroll) {
        if (!controlPressed) {
            scroll.consume();
            return;
        }
        double zoom_fac = 1.05;
        double delta_y = scroll.getDeltaY();
        if (delta_y < 0) {
            zoom_fac = 2.0 - zoom_fac;
        }
        Scale newScale = new Scale();
        newScale.setPivotX(scroll.getSceneX());
        newScale.setPivotY(scroll.getSceneY());
        newScale.setX(mazeDisplayer.getScaleX() * zoom_fac);
        newScale.setY(mazeDisplayer.getScaleY() * zoom_fac);
//        mazePane.toBack();
        mazeDisplayer.getTransforms().add(newScale);
        scroll.consume();
    }


    public void keyRelease(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.CONTROL)
            controlPressed = false;
        keyEvent.consume();
    }

    public void setResizeEvent(Scene scene) {
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                Scale newScale = new Scale();
                double old = oldSceneWidth.doubleValue() - vb.getWidth();
                double neww = newSceneWidth.doubleValue() - vb.getWidth();
                newScale.setPivotX(mazeDisplayer.getLayoutX() * (neww) / (old));
                newScale.setX(mazeDisplayer.getScaleX() * (neww) / (old));
                mazeDisplayer.getTransforms().add(newScale);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                Scale newScale = new Scale();
                double old = oldSceneHeight.doubleValue() - menu.getHeight();
                double neww = newSceneHeight.doubleValue() - menu.getHeight();
                newScale.setPivotY(mazeDisplayer.getLayoutY() * (neww) / (old));
                newScale.setY(mazeDisplayer.getScaleY() * (neww) / (old));
                mazeDisplayer.getTransforms().add(newScale);
            }
        });
    }
//    public void setResizeEvent(Scene scene) {
//        scene.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
//                mazeDisplayer.redraw();
//                Scale newScale = new Scale();
//                newScale.setPivotX(scene.getX() * (newSceneWidth.doubleValue() - vb.getMaxWidth()) / (oldSceneWidth.doubleValue() - vb.getMaxWidth()));
//                newScale.setX(mazeDisplayer.getScaleX() * (newSceneWidth.doubleValue() - vb.getMaxWidth()) / (oldSceneWidth.doubleValue() - vb.getMaxWidth()));
////                mazeDisplayer.getTransforms().add(newScale);
//                vb.getTransforms().add(newScale);
//                menu.getTransforms().add(newScale);
//            }
//        });
//        scene.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
//                mazeDisplayer.redraw();
//                Scale newScale = new Scale();
//                newScale.setPivotY(scene.getY() * (newSceneHeight.doubleValue() - menu.getMaxHeight()) / (oldSceneHeight.doubleValue() - menu.getMaxHeight()));
//                newScale.setY(mazeDisplayer.getScaleY() * (newSceneHeight.doubleValue() - menu.getMaxHeight()) / (oldSceneHeight.doubleValue() - menu.getMaxHeight()));
//                mazeDisplayer.getTransforms().add(newScale);
//                vb.getTransforms().add(newScale);
//                menu.getTransforms().add(newScale);
//            }
//        });
//    }

    public void DragMove(MouseEvent drg) {
        double basicHeight = mazeDisplayer.getHeight() / viewModel.getMaze().length;
        double basicWidth = mazeDisplayer.getWidth() / viewModel.getMaze()[0].length;
        boolean right = drg.getX() > lastX + basicWidth;
        boolean left = drg.getX() < lastX - basicWidth;
        boolean down = drg.getY() > lastY + basicHeight;
        boolean up = drg.getY() < lastY - basicHeight;
        if (right) {
            lastX = drg.getX();
            viewModel.moveCharacter(KeyCode.NUMPAD6);
        }
        if (left) {
            lastX = drg.getX();
            viewModel.moveCharacter(KeyCode.NUMPAD4);
        }
        if (down) {
            lastY = drg.getY();
            viewModel.moveCharacter(KeyCode.NUMPAD2);
        }
        if (up) {
            lastY = drg.getY();
            viewModel.moveCharacter(KeyCode.NUMPAD8);
        }
    }


    public void Close(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            viewModel.stopModel();
            Stage stage = (Stage) mazeDisplayer.getScene().getWindow();
            stage.close();
            actionEvent.consume();
        } else {
            actionEvent.consume();
        }
    }


}
