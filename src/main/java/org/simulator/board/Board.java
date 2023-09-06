package org.simulator.board;

import lombok.Getter;
import org.simulator.board.Components.Empty;
import org.simulator.board.Components.Tile;
import org.simulator.controls.Mode;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;


public class Board extends JPanel{
    @Getter
    private Tile[][] board;
    @Getter
    private final int xDimension;
    @Getter
    private final int yDimension;

    public Board(int xDimension,int yDimension){
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.initializeBoard(xDimension,yDimension);
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(Color.WHITE);
        this.setFocusable(false);
    }
    private void initializeBoard(int xDimension,int yDimension){
        this.board = new Tile[xDimension][yDimension];
        this.setLayout(new GridLayout(xDimension,yDimension));
        for (int i = 0; i < xDimension; i++){
            for(int j = 0; j < yDimension; j++){
                this.board[i][j] = new Empty(i,j);
                this.add(this.board[i][j]);
            }
        }
    }
    public void reloadBoard(){
        this.removeAll();
        for (int i = 0; i < xDimension; i++){
            for(int j = 0; j < yDimension; j++){
                this.add(this.board[i][j]);
            }
        }
    }
    //this method is for further development and will be useful when the simulation part of the project is added
    public void changeBoardMode(Mode mode){
        switch (mode) {
            case DRAW_MODE -> this.setBackground(Color.WHITE);
            case SIMULATION_MODE -> this.setBackground(Color.GRAY);
        }
    }
    public void replaceTile(Tile tile, StateType stateType){
        int coordinateX = tile.getXCoordinate();
        int coordinateY = tile.getYCoordinate();
        try {
            // Create a new instance of the specified class based on StateType, still black magic
            Class<? extends Tile> tileClass = stateType.getTileClass();
            Tile newTile = tileClass.getDeclaredConstructor(int.class, int.class).newInstance(coordinateX, coordinateY);

            this.board[coordinateX][coordinateY] = newTile;

            disconnect(coordinateX, coordinateY);
            reloadBoard();
            this.revalidate();
            this.repaint();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    public void smartTileConnectDisconnect(Tile tile1, Tile tile2){
        int dx = tile2.getXCoordinate() - tile1.getXCoordinate();
        int dy = tile2.getYCoordinate() - tile1.getYCoordinate();
        if(tile1.getStateType() == StateType.EMPTY || tile2.getStateType() == StateType.EMPTY){
            return;
        }
        if (dx == 1 && dy == 0) {
            tile1.getConnections().put(Direction.DOWN,!tile1.getConnections().get(Direction.DOWN));
            tile2.getConnections().put(Direction.UP,!tile2.getConnections().get(Direction.UP));
        } else if (dx == -1 && dy == 0) {
            tile1.getConnections().put(Direction.UP,!tile1.getConnections().get(Direction.UP));
            tile2.getConnections().put(Direction.DOWN,!tile2.getConnections().get(Direction.DOWN));
        } else if (dx == 0 && dy == 1) {
            tile1.getConnections().put(Direction.RIGHT,!tile1.getConnections().get(Direction.RIGHT));
            tile2.getConnections().put(Direction.LEFT,!tile2.getConnections().get(Direction.LEFT));
        } else if (dx == 0 && dy == -1) {
            tile1.getConnections().put(Direction.LEFT,!tile1.getConnections().get(Direction.LEFT));
            tile2.getConnections().put(Direction.RIGHT,!tile2.getConnections().get(Direction.RIGHT));
        }
    }
    private void disconnect(int coordinateX, int coordinateY){
        if (coordinateX != 0 ) {
            this.board[coordinateX - 1][coordinateY].getConnections().put(Direction.DOWN,false);
        }
        if (coordinateX < xDimension-1) {
            this.board[coordinateX + 1][coordinateY].getConnections().put(Direction.UP,false);
        }
        if (coordinateY != 0){
            this.board[coordinateX][coordinateY - 1].getConnections().put(Direction.RIGHT,false);
        }
        if (coordinateY < yDimension-1) {
            this.board[coordinateX][coordinateY + 1].getConnections().put(Direction.LEFT,false);
        }
    }
    public void TestStatus(Tile tile){
        for(int x = 0; x < this.xDimension; x++){
            for(int y = 0; y < this.yDimension; y++){
                if(Objects.equals(this.board[x][y], tile)){
                    System.out.println("x:" + x +"; y:" + y);
                    System.out.println("type:" + this.board[x][y].getStateType());
                    int connectionCounter = 0;
                    for(Direction direction : Direction.values()){
                        if(this.board[x][y].getConnections().get(direction)){
                            connectionCounter++;
                        }
                    }
                    System.out.println("number of connections: " + connectionCounter);

                    return;
                }
            }
        }
    }
}
