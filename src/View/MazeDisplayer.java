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
import java.util.Random;

/**
 * Created by Aviadjo on 3/9/2017.
 */
public class MazeDisplayer extends Canvas {

    private int[][] maze;
    private Position goalPosition;
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;

    public StringProperty pokemon_name = new SimpleStringProperty();

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
//            double canvasHeight = Math.min(getHeight(),getWidth());
            double canvasWidth = getWidth();
//            double canvasWidth = Math.min(getHeight(),getWidth());
            double cellHeight = canvasHeight / maze.length;
            double cellWidth = canvasWidth / maze[0].length;
            double cellsize = Math.min(cellHeight, cellWidth);


            Image wallImage = new Image(ClassLoader.getSystemResourceAsStream("Images/wall4.jpg"));
            Image walkImage = new Image(ClassLoader.getSystemResourceAsStream("Images/walk.jpg"));
            Image characterImage = new Image(ClassLoader.getSystemResourceAsStream("Images/character4.jpg"));
            Image EndImage = new Image(ClassLoader.getSystemResourceAsStream("Images/pokemon/"+pokemon_name.getValue()+".jpg"));

            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());

            //Draw Maze
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j] == 1) {
                        //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                        gc.drawImage(wallImage, j * cellsize, i * cellsize, cellsize, cellsize);
                    } else
                        gc.drawImage(walkImage, j * cellsize, i * cellsize, cellsize, cellsize);
                    if (goalPosition.equals(i, j))
                        gc.drawImage(EndImage, j * cellsize, i * cellsize, cellsize, cellsize);

                }
            }
            gc.drawImage(characterImage, characterPositionColumn * cellsize, characterPositionRow * cellsize, cellsize, cellsize);

        }
    }

    public void drawSolution(List<Pair<Integer, Integer>> solution) {
        if (solution != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.length;
            double cellWidth = canvasWidth / maze[0].length;
            double cellsize = Math.min(cellHeight, cellWidth);

            Image SolutionImage = new Image(ClassLoader.getSystemResourceAsStream("Images/pokemon/Pikachu.jpg"));
            GraphicsContext gc = getGraphicsContext2D();
            //Draw Maze
            for (int i = 0; i < solution.size() - 1; i++) {
                if (!solution.get(i).equals(new Pair<>(characterPositionRow, characterPositionColumn)))
                    gc.drawImage(SolutionImage, solution.get(i).getValue() * cellsize, solution.get(i).getKey() * cellsize, cellsize, cellsize);
            }
            //e.printStackTrace();
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
