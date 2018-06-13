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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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

    public void drawSolution(Solution solution) throws Exception {
        if (solution != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.length;
            double cellWidth = canvasWidth / maze[0].length;

            ArrayList<AState> SolutionPath = solution.getSolutionPath();
            try {
                Image SolutionImage = new Image(new FileInputStream(ImageFileNameSolution.get()));////////////

                GraphicsContext gc = getGraphicsContext2D();
//                gc.clearRect(0, 0, getWidth(), getHeight());

                //Draw Maze
                for (int i = 0; i < SolutionPath.size() - 1; i++) {
                    Position p;
                    if (SolutionPath.get(i) instanceof MazeState)
                        p = ((MazeState) SolutionPath.get(i)).getCurrent_position();
                    else
                        throw new Exception("Solution State should be MazeState");
                    gc.drawImage(SolutionImage, p.getColumnIndex() * cellWidth, p.getRowIndex() * cellHeight, cellWidth, cellHeight);
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
