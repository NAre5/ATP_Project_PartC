package Model;

import Client.Client;
import IO.MyDecompressorInputStream;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import Client.IClientStrategy;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Server.*;
/**
 * Created by Aviadjo on 6/14/2017.
 */
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
            notifyObservers();
        });
    }


    @Override
    public int[][] getMaze() {
        return maze.getMaze();
    }

    @Override
    public void moveCharacter(KeyCode movement) {
        try {
            switch (movement) {
                case UP:
                    if (maze.getMaze()[characterPositionRow - 1][characterPositionColumn] == 0)
                        characterPositionRow--;
                    break;
                case DOWN:
                    if (maze.getMaze()[characterPositionRow + 1][characterPositionColumn] == 0)
                        characterPositionRow++;
                    break;
                case RIGHT:
                    if (maze.getMaze()[characterPositionRow][characterPositionColumn + 1] == 0)
                        characterPositionColumn++;
                    break;
                case LEFT:
                    if (maze.getMaze()[characterPositionRow][characterPositionColumn - 1] == 0)
                        characterPositionColumn--;
                    break;
            }
            setChanged();
            notifyObservers();
        } catch (ArrayIndexOutOfBoundsException e) {
        }

    }

    @Override
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    @Override
    public Solution getMazeSolution() {

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
                            Solution mazeSolution = (Solution)fromServer.readObject();
                        } catch (Exception var10) {
                            var10.printStackTrace();
                        }

                    }
                });
                client.communicateWithServer();
            } catch (UnknownHostException var1) {
                var1.printStackTrace();
            }
        });


        return null;
    }

    @Override
    public Position getGoalPosition() {
        return maze.getGoalPosition();
    }
}
