package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;

import java.io.File;
import java.util.List;

/**
 * Created by Aviadjo on 6/14/2017.
 */
public interface IModel {
    void generateMaze(int width, int height);
    void moveCharacter(KeyCode movement);
    int[][] getMaze();
    List<Pair<Integer,Integer>> getSolution();
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    void generateSolution();
    Position getGoalPosition();
//    void saveMaze(String name);
    void saveMaze(File file);
    void loadMaze(File file);
    Position getStartPosition();
    void stopServers();
//    Solution getSolution();
}
