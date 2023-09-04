package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Empty extends Tile{
    public Empty(int xCoordinate, int yCoordinate){
        super(StateType.EMPTY,StateType.EMPTY.getMinConnections(),StateType.EMPTY.getMaxConnections());
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnection();

    }

    public void action(){

    }
}
