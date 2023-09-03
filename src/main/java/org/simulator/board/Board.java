package org.simulator.board;

import lombok.Getter;
import org.simulator.board.Components.*;
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
                this.board[i][j] = new Empty();
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
        }
    }
    public void replace(int x,int y,StateType stateType){
        switch (stateType) {
            case CHAMBER -> this.board[x][y] = new Chamber();
            case EMPTY -> this.board[x][y] = new Empty();
            case FORE_VACUUM_PUMP -> this.board[x][y] = new ForeVacuumPump();
            case GAUGE -> this.board[x][y] = new Gauge();
            case HV_PUMP -> this.board[x][y] = new HVPump();
            case NONE -> this.board[x][y] = new None();
            case PIPE -> this.board[x][y] = new Pipe();
            case PUMP_STAND -> this.board[x][y] = new PumpStand();
            case VALVE -> this.board[x][y] = new Valve();
            case VENTING_VALVE -> this.board[x][y] = new VentingValve();
        }
        this.repaint();
    }
    public void changeTile(Tile tile, StateType stateType){
        for(int x = 0; x < this.xDimension; x++){
            for(int y = 0; y <this.yDimension; y++){
                if(Objects.equals(this.board[x][y], tile)){
                    if(stateType == StateType.EMPTY){
                        disconnectTiles(x,y);
                    }else{
                        connectTiles(x,y);
                    }
                    replace(x,y,stateType);
                    return;
                }
            }
        }
    }
    public void connectTiles(int x, int y){
        if (x != 0 && !(this.board[x - 1][y].getStateType() == StateType.EMPTY)) {
            this.board[x][y].connectTile(Direction.UP);
            this.board[x - 1][y].connectTile(Direction.DOWN);
        }
        if (x != xDimension - 1 && !(this.board[x + 1][y].getStateType() == StateType.EMPTY)) {
            this.board[x][y].connectTile(Direction.DOWN);
            this.board[x + 1][y].connectTile(Direction.UP);
        }
        if (y != 0 && !(this.board[x][y - 1].getStateType() == StateType.EMPTY)){
            this.board[x][y].connectTile(Direction.LEFT);
            this.board[x][y - 1].connectTile(Direction.RIGHT);
        }
        if (y != yDimension - 1 && !(this.board[x][y + 1].getStateType() == StateType.EMPTY)) {
            this.board[x][y].connectTile(Direction.RIGHT);
            this.board[x][y + 1].connectTile(Direction.LEFT);
        }
    }
    public void disconnectTiles(int x, int y){
        if (x != 0 && !(this.board[x - 1][y].getStateType() == StateType.EMPTY)) {
            this.board[x][y].disconnectTile(Direction.UP);
            this.board[x - 1][y].disconnectTile(Direction.DOWN);
        }
        if (x != xDimension - 1 && !(this.board[x + 1][y].getStateType() == StateType.EMPTY)) {
            this.board[x][y].disconnectTile(Direction.DOWN);
            this.board[x + 1][y].disconnectTile(Direction.UP);
        }
        if (y != 0 && !(this.board[x][y - 1].getStateType() == StateType.EMPTY)){
            this.board[x][y].disconnectTile(Direction.LEFT);
            this.board[x][y - 1].disconnectTile(Direction.RIGHT);
        }
        if (y != yDimension - 1 && !(this.board[x][y + 1].getStateType() == StateType.EMPTY)) {
            this.board[x][y].disconnectTile(Direction.RIGHT);
            this.board[x][y + 1].disconnectTile(Direction.LEFT);
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
//    public void TestStatus(Tile tile){
//        for(int x = 0; x < this.xDimension; x++){
//            for(int y = 0; y < this.yDimension; y++){
//                if(Objects.equals(this.board[x][y], tile)){
//                    System.out.println("x:" + x +"; y:" + y);
//                    System.out.println("type:" + this.board[x][y].getStateType());
//                    int numberOfCon = 0;
//                    for(Direction direction : Direction.values()){
//                        if( this.board[x][y].getNeighbours().containsKey(direction)){
//                            if(this.board[x][y].getNeighbours().get(direction).isConnected())
//                            numberOfCon++;
//                        }
//                    }
//                    System.out.println("number of connections: " + numberOfCon);
//
//                    return;
//                }
//            }
//        }
//    }
}
