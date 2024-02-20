package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Empty extends Tile{
    public Empty(int xCoordinate, int yCoordinate){
        super(StateType.EMPTY);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();
    }
}
