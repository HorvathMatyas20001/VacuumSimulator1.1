package org.simulator.data.openFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simulator.board.Board;
import org.simulator.board.Components.Tile;
import org.simulator.board.Direction;
import org.simulator.board.StateType;
import org.simulator.controls.SimulatorLogic;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Testloadboard {
    public Testloadboard(SimulatorLogic logic){
        this.logic = logic;
    }
    private SimulatorLogic logic;

    public void loadBoard(){
        Board board = readInBoard();
        board.addMouseListener(logic);
        board.addMouseMotionListener(logic);
        logic.setBoard(board);
        logic.getMainFrame().add(board);
        logic.getBoard().revalidate();
        logic.getBoard().reloadBoard();
    }
    public Board readInBoard(){
        try {
            JSONObject jsonObject = new JSONObject(Files.readString(Paths.get("C:/Users/mutyi/OneDrive/Desktop/3.json")));
            JSONArray boardArray = jsonObject.getJSONArray("Board");

            Board board = new Board(boardArray.length(), boardArray.getJSONArray(0).length());

            loadComponents(boardArray,board);

            loadConnections( boardArray,board);

            return board;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // Handle the exception if the file cannot be read or if there is an error parsing the JSON data
            return null;
        }
    }
    private void loadComponents(JSONArray boardArray,Board board){
        for (int i = 0; i < boardArray.length(); i++) {
            JSONArray rowArray = boardArray.getJSONArray(i);
            for (int j = 0; j < boardArray.getJSONArray(0).length(); j++) {
                JSONObject tileObject = rowArray.getJSONObject(j);
                try {
                    Class<? extends Tile> tileClass = StateType.valueOf(tileObject.getString("State")).getTileClass();
                    Tile newTile = tileClass.getDeclaredConstructor(int.class, int.class).newInstance(i, j);
                    board.getBoard()[i][j] = newTile;
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void loadConnections(JSONArray boardArray,Board board){
        for (int i = 0; i < boardArray.length(); i++) {
            JSONArray rowArray = boardArray.getJSONArray(i);
            for (int j = 0; j < boardArray.getJSONArray(0).length(); j++) {
                JSONObject tileObject = rowArray.getJSONObject(j);
                JSONObject connectionsObject = tileObject.getJSONObject("connections");
                for (Direction direction : Direction.values()) {
                    board.getBoard()[i][j].getConnections().put(direction,connectionsObject.getBoolean(direction.name()));
                }
            }
        }
    }
    public void printBoardState(Board board){
        System.out.println("board:");
        int xDimension = board.getXDimension(); //currentRows
        int yDimension =  board.getYDimension(); //currentCols
        for (int i = 0; i < xDimension; i++) {
            for (int j = 0; j < yDimension; j++) {
                System.out.print("[" + board.getBoard()[i][j].getStateType() + "]");
            }
            System.out.print("\n");
        }
    }
}

