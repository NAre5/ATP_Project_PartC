package View;

import Server.Server;
import View.AView;
import View.MazeDisplayer;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class GameView extends AView implements Initializable {

    public MazeDisplayer mazeDisplayer;
    public Button Solve;
    public MenuBar menu;
    boolean controlPressed = false;
    private double lastX;
    private double lastY;

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

//    public void saveMaze(ActionEvent actionEvent) {
//        try {
//            Stage stage = new Stage();
//            stage.setTitle("Save maze");
////            FXMLLoader fxmlLoader = new FXMLLoader();
////            Parent root = fxmlLoader.load(getClass().getResource("AboutView.fxml").openStream());
//            Pane pane = new Pane();
//            VBox vb = new VBox();
//            Label lbl = new Label("Enter maze name:");
//            Button btn = new Button("save");
//            TextField txt = new TextField();
//            vb.getChildren().addAll(lbl, txt, btn);
//            pane.getChildren().addAll(vb);
//            Scene scene = new Scene(pane, 200, 150);
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
//                   showAlert("File name can not contain /:*?<>|\"\\");
//                   ev.consume();
//                }
//                try
//                {
//                    viewModel.saveMaze(name);
//                    stage.close();
//                }
//                catch (IllegalArgumentException e)
//                {
//                    showAlert("File name already exists");
//                    ev.consume();
//                }
//            });
//
//        } catch (Exception e) {
//
//        }
//    }

    //    public void loadMaze(ActionEvent actionEvent) {
//        try {
//            Stage stage = new Stage();
//            stage.setTitle("Load maze");
////            FXMLLoader fxmlLoader = new FXMLLoader();
////            Parent root = fxmlLoader.load(getClass().getResource("AboutView.fxml").openStream());
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
//                    viewModel.loadMaze(name);
//                    stage.close();
//                }
//                catch (IllegalArgumentException e)
//                {
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
        File file = loadFile();
        if (file == null)
            return;
        String path = file.getAbsolutePath();
        viewModel.loadMaze(new File(path));
        event.consume();
    }

    private File loadFile() {
        JFileChooser fileChooser = new JFileChooser("Mazes");
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            return fileChooser.getSelectedFile();
        return null;
    }

    public void saveMaze(ActionEvent event) {
        File file = saveFile();
        if (file == null)
            return;
        String path = file.getAbsolutePath();
        viewModel.saveMaze(new File(path));
    }

    private File saveFile() {
        JFileChooser fileChooser = new JFileChooser("Mazes");
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            return file;
        }
        return null;
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

    //    public void setResizeEvent(Scene scene) {
////        long width = 0;
////        long height = 0;
////        scene.widthProperty().addListener(new ChangeListener<Number>() {
////            @Override
////            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
////                Scale newScale = new Scale();
////                newScale.setPivotX(scene.getX() * (newSceneWidth.doubleValue()) / (oldSceneWidth.doubleValue()));
////                newScale.setX(mazeDisplayer.getScaleX() * (newSceneWidth.doubleValue()) / (oldSceneWidth.doubleValue()));
////                mazeDisplayer.getTransforms().add(newScale);
////            }
////        });
////        scene.heightProperty().addListener(new ChangeListener<Number>() {
////            @Override
////            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
////                Scale newScale = new Scale();
////                newScale.setPivotY(scene.getY() * (newSceneHeight.doubleValue() - menu.getMaxHeight()) / (oldSceneHeight.doubleValue() - menu.getMaxHeight()));
////                newScale.setY(mazeDisplayer.getScaleY() * (newSceneHeight.doubleValue() - menu.getMaxHeight()) / (oldSceneHeight.doubleValue() - menu.getMaxHeight()));
////                mazeDisplayer.getTransforms().add(newScale);
////            }
////        });
////    }
    public void setResizeEvent(Scene scene) {
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mazeDisplayer.redraw();
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mazeDisplayer.redraw();
            }
        });
    }

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

}



