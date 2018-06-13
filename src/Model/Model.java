package Model;

import Client.Client;
import IO.MyDecompressorInputStream;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import Client.IClientStrategy;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Server.*;
import javafx.util.Pair;


public class Model extends Observable implements IModel {

    private ExecutorService threadPool = Executors.newCachedThreadPool();
    Server mgs;
    Server sss;

    public Model() {
        mgs = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        sss = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
    }

    public void startServers() {
        mgs.start();
        sss.start();
    }

    public void stopServers() {
        mgs.stop();
        sss.stop();
    }

    private Maze maze;

    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;

    @Override
    public Position getGoalPosition() {
        return maze.getGoalPosition();
    }


    //if problem probably here because maze
    @Override
    public void generateMaze(int width, int height) {
        //Generate maze

        threadPool.execute(() -> {
            try {
                Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                    @Override
                    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                        try {
                            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                            toServer.flush();
                            int[] mazeDimensions = new int[]{width, height};
                            toServer.writeObject(mazeDimensions); //send maze dimensions to server
                            toServer.flush();
                            byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                            InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                            byte[] decompressedMaze = new byte[100000 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                            is.read(decompressedMaze); //Fill decompressedMaze with bytes
                            maze = new Maze(decompressedMaze);
                            characterPositionColumn = maze.getStartPosition().getColumnIndex();
                            characterPositionRow = maze.getStartPosition().getRowIndex();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                client.communicateWithServer();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            setChanged();
            notifyObservers("maze");
        });
    }


    @Override
    public int[][] getMaze() {
        return maze.getMaze();
    }


    private KeyCode last = KeyCode.DOWN;

    @Override
    public void moveCharacter(KeyCode movement) {
        try {
            switch (movement) {
                case UP:
                    if (canGoTo(-1, 0))
                        characterPositionRow--;
                    break;
                case NUMPAD8:
                    if (canGoTo(-1, 0))
                        characterPositionRow--;
                    break;
                case DOWN:
                    if (canGoTo(1, 0))
                        characterPositionRow++;
                    break;
                case NUMPAD2:
                    if (canGoTo(1, 0))
                        characterPositionRow++;
                    break;
                case RIGHT:
                    if (canGoTo(0, 1))
                        characterPositionColumn++;
                    break;
                case NUMPAD6:
                    if (canGoTo(0, 1))
                        characterPositionColumn++;
                    break;
                case LEFT:
                    if (canGoTo(0, -1))
                        characterPositionColumn--;
                    break;
                case NUMPAD4:
                    if (canGoTo(0, -1))
                        characterPositionColumn--;
                    break;
                case NUMPAD1:
                    if (canGoTo(1, -1)) {
                        if (canGoTo(0, -1) || canGoTo(1, 0)) {
                            characterPositionRow++;
                            characterPositionColumn--;
                        }
                    }
                    break;
                case NUMPAD3:
                    if (canGoTo(1, 1)) {
                        if (canGoTo(1, 0) || canGoTo(0, 1)) {
                            characterPositionRow++;
                            characterPositionColumn++;
                        }
                    }
                    break;
                case NUMPAD9:
                    if (canGoTo(-1, 1)) {
                        if (canGoTo(-1, 0) || canGoTo(0, 1)) {
                            characterPositionRow--;
                            characterPositionColumn++;
                        }
                    }
                    break;
                case NUMPAD7:
                    if (canGoTo(-1, -1)) {
                        if (canGoTo(-1, 0) || canGoTo(0, -1)) {
                            characterPositionRow--;
                            characterPositionColumn--;
                        }
                    }
                    break;
                case END:
                    while (true) {
                        int cpr = characterPositionRow, cpc = characterPositionColumn;
                        moveCharacter(last);
                        if (cpr == characterPositionRow && cpc == characterPositionColumn)
                            break;
                    }

            }
            if (movement != KeyCode.END && ((movement.isKeypadKey() && movement.isDigitKey()) || (movement.isArrowKey())))
                last = movement;
            setChanged();
            notifyObservers();
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    private boolean canGoTo(int d_i, int d_j) {
        return maze.getMaze()[characterPositionRow + d_i][characterPositionColumn + d_j] == 0;
    }

    @Override
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public List<Pair<Integer, Integer>> solutionPath = new ArrayList<>();

    @Override
    public void generateSolution() {

        threadPool.execute(() -> {

            try {
                Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                        try {
                            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                            toServer.flush();
                            toServer.writeObject(maze);
                            toServer.flush();
                            Solution mazeSolution = (Solution) fromServer.readObject();
                            for (AState state : mazeSolution.getSolutionPath()) {
                                Position p = ((MazeState) state).getCurrent_position();
                                solutionPath.add(new Pair<>(p.getRowIndex(), p.getColumnIndex()));
                            }


                        } catch (Exception var10) {
                            var10.printStackTrace();
                        }

                    }
                });
                client.communicateWithServer();
            } catch (UnknownHostException var1) {
                var1.printStackTrace();
            }
            setChanged();
            notifyObservers("solution");
        });

    }

    @Override
    public List<Pair<Integer, Integer>> getSolution() {
        return solutionPath;
    }

    public void saveMaze(String name) {
        File theDir = new File("Mazes");
        theDir.mkdir();
        ObjectOutputStream outputStream = null;

        try {
            File maze_file = new File("Mazes/" + name);
            if (maze_file.exists()) {
                throw new IllegalArgumentException();
            } else {
                outputStream = new ObjectOutputStream(new FileOutputStream(maze_file));
                outputStream.writeObject(maze);
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception var33) {
            ;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException var29) {
                var29.printStackTrace();
            }

        }
    }

    @Override
    public Maze loadMaze(String name) {
        ObjectInputStream ois = null;
        Maze loadMaze = null;
        try {
            File maze_file = new File("Mazes/" + name);//The path of the new file is the hash code for this maze
            if (maze_file.exists()) {//if the file exists, it is means we solve thus maze in the past and we just need to read the file.
                ois = new ObjectInputStream(new FileInputStream(maze_file.getPath()));
                loadMaze = (Maze) ois.readObject();
                maze = loadMaze;
            } else {//if we did not solve this maze in past.
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception ignored) {
        } finally { /*Safe close of the streams */
            try {
                if (ois != null)
                    ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return loadMaze;
    }

}
