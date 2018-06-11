package View.MainPage;

import View.AView;
import View.MazeDisplayer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MainView extends AView implements Initializable,Observer {

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
}
