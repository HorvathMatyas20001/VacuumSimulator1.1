package org.simulator.board;

import lombok.Getter;
import org.simulator.controls.Mode;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class Board extends JPanel{
    @Getter
    private Tile[][] board;
    @Getter
    private int xDimension;
    @Getter
    private int yDimension;

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
                this.board[i][j] = new Tile();
                this.add(this.board[i][j]);
            }
        }
    }
    public void changeBoardMode(Mode mode){
        switch(mode) {
            case DRAW_MODE:
                this.setBackground(Color.WHITE);
                break;
            case NONE:
                this.setBackground(Color.WHITE);
                break;
            case CHECK_CONNECTION_MODE:
                this.setBackground(Color.GRAY);
                break;
        }
        for (int i = 0; i < xDimension; i++) {
            for (int j = 0; j < yDimension; j++) {
                this.board[i][j].setMode(mode);
            }
        }
    }
    public void replace(int x,int y,StateType stateType){
        this.board[x][y].setStateType(stateType);
        this.board[x][y].setTooFewConnections(false);
        this.board[x][y].setTooFewConnections(false);
        this.repaint();
    }
    public void changeTile(Tile tile, StateType stateType){
        for(int x = 0; x < this.xDimension; x++){
            for(int y = 0; y <this.yDimension; y++){
                if(Objects.equals(this.board[x][y], tile)){
                    if(stateType == StateType.EMPTY){
                        removeNeighbours(x,y);
                    }else{
                        connectWithNeighbours(x,y);
                    }
                    replace(x,y,stateType);
                    return;
                }
            }
        }
    }
    public void connectWithNeighbours(int x, int y){
        if (x != 0 && !(this.board[x - 1][y].getStateType() == StateType.EMPTY)) {
            this.board[x][y].addNeighbour(Direction.UP,this.board[x - 1][y]);
            this.board[x - 1][y].addNeighbour(Direction.DOWN,this.board[x][y]);
        }
        if (x != xDimension - 1 && !(this.board[x + 1][y].getStateType() == StateType.EMPTY)) {
            this.board[x][y].addNeighbour(Direction.DOWN,this.board[x + 1][y]);
            this.board[x + 1][y].addNeighbour(Direction.UP,this.board[x][y]);
        }
        if (y != 0 && !(this.board[x][y - 1].getStateType() == StateType.EMPTY)){
            this.board[x][y].addNeighbour(Direction.LEFT,this.board[x][y - 1]);
            this.board[x][y - 1].addNeighbour(Direction.RIGHT,this.board[x][y]);
        }
        if (y != yDimension - 1 && !(this.board[x][y + 1].getStateType() == StateType.EMPTY)) {
            this.board[x][y].addNeighbour(Direction.RIGHT,this.board[x][y + 1]);
            this.board[x][y + 1].addNeighbour(Direction.LEFT,this.board[x][y]);
        }
    }
    public void removeNeighbours(int x, int y){
        if (x != 0 && !(this.board[x - 1][y].getStateType() == StateType.EMPTY)) {
            this.board[x][y].removeNeighbour(Direction.UP);
            this.board[x - 1][y].removeNeighbour(Direction.DOWN);
        }
        if (x != xDimension - 1 && !(this.board[x + 1][y].getStateType() == StateType.EMPTY)) {
            this.board[x][y].removeNeighbour(Direction.DOWN);
            this.board[x + 1][y].removeNeighbour(Direction.UP);
        }
        if (y != 0 && !(this.board[x][y - 1].getStateType() == StateType.EMPTY)){
            this.board[x][y].removeNeighbour(Direction.LEFT);
            this.board[x][y - 1].removeNeighbour(Direction.RIGHT);
        }
        if (y != yDimension - 1 && !(this.board[x][y + 1].getStateType() == StateType.EMPTY)) {
            this.board[x][y].removeNeighbour(Direction.RIGHT);
            this.board[x][y + 1].removeNeighbour(Direction.LEFT);
        }
    }
    public Tile findTile(Tile tile){
        for(int x = 0; x < this.xDimension; x++){
            for(int y = 0; y < this.yDimension; y++){
                if(Objects.equals(this.board[x][y], tile)){
                    return this.board[x][y];
                }
            }
        }
        return null;
    }
    public void TestStatus(Tile tile){
        for(int x = 0; x < this.xDimension; x++){
            for(int y = 0; y < this.yDimension; y++){
                if(Objects.equals(this.board[x][y], tile)){
                    System.out.println("x:" + x +"; y:" + y);
                    System.out.println("type:" + this.board[x][y].getStateType());
                    int numberOfCon = 0;
                    for(Direction direction : Direction.values()){
                        if( this.board[x][y].getNeighbours().containsKey(direction)){
                            if(this.board[x][y].getNeighbours().get(direction).isConnected())
                            numberOfCon++;
                        }
                    }
                    System.out.println("number of connections: " + numberOfCon);

                    return;
                }
            }
        }
    }
}
