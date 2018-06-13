package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aviadjo on 3/9/2017.
 */
public class MazeDisplayer extends Canvas {

    private int[][] maze;
    private Position goalPosition;
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;

    public void setMaze(int[][] maze, Position goalPosition) {
        this.maze = maze;
        this.goalPosition = goalPosition;

        redraw();
    }

    public void setCharacterPosition(int row, int column) {
        characterPositionRow = row;
        characterPositionColumn = column;
        redraw();
    }

    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public void redraw() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.length;
            double cellWidth = canvasWidth / maze[0].length;

            try {
                Image wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));
                Image walkImage = new Image(new FileInputStream(ImageFileNameWalk.get()));
                Image characterImage = new Image(new FileInputStream(ImageFileNameCharacter.get()));
                Image EndImage = new Image(new FileInputStream(ImageFileNameEnd.get()));

                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());

                //Draw Maze
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[i].length; j++) {
                        if (goalPosition.equals(i, j))
                            gc.drawImage(EndImage, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        else if (maze[i][j] == 1) {
                            //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(wallImage, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        } else
                            gc.drawImage(walkImage, j * cellWidth, i * cellHeight, cellWidth, cellHeight);


                    }
                }

                //Draw Character
                //gc.setFill(Color.RED);
                //gc.fillOval(characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
                gc.drawImage(characterImage, characterPositionColumn * cellWidth, characterPositionRow * cellHeight, cellWidth, cellHeight);
            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }
        }
    }

    public void drawSolution(List<Pair<Integer, Integer>> solution) {
        if (solution != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.length;
            double cellWidth = canvasWidth / maze[0].length;

            try {
                Image SolutionImage = new Image(new FileInputStream(ImageFileNameSolution.get()));////////////

                GraphicsContext gc = getGraphicsContext2D();
//                gc.clearRect(0, 0, getWidth(), getHeight());

                //Draw Maze
                for (int i = 1; i < solution.size() - 1; i++) {
//check good value and key <==> column row
                    if (!solution.get(i).equals(new Pair<>(characterPositionRow,characterPositionColumn)))
                        gc.drawImage(SolutionImage, solution.get(i).getValue() * cellWidth, solution.get(i).getKey() * cellHeight, cellWidth, cellHeight);
                }
            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }
        }

    }

    //region Properties
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameWalk = new SimpleStringProperty();
    private StringProperty ImageFileNameEnd = new SimpleStringProperty();
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();
    private StringProperty ImageFileNameSolution = new SimpleStringProperty();

    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }


    public String getImageFileNameWalk() {
        return ImageFileNameWalk.get();
    }

    public void setImageFileNameWalk(String imageFileNameWalk) {
        this.ImageFileNameWalk.set(imageFileNameWalk);
    }


    public String getImageFileNameEnd() {
        return ImageFileNameEnd.get();
    }

    public void setImageFileNameEnd(String imageFileNameEnd) {
        this.ImageFileNameEnd.set(imageFileNameEnd);
    }


    public String getImageFileNameCharacter() {
        return ImageFileNameCharacter.get();
    }

    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.ImageFileNameCharacter.set(imageFileNameCharacter);
    }

    public String getImageFileNameSolution() {
        return ImageFileNameSolution.get();
    }

    public void setImageFileNameSolution(String imageFileNameSolution) {
        this.ImageFileNameSolution.set(imageFileNameSolution);
    }

    //endregion

}
