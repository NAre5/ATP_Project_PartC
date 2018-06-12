package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

/**
 * Created by Aviadjo on 6/14/2017.
 */
public interface IModel {
    void generateMaze(int width, int height);
    void moveCharacter(KeyCode movement);
    int[][] getMaze();
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    Solution getMazeSolution();
    Position getGoalPosition();
    void saveMaze(String name);
    Maze loadMaze(String name);
}
