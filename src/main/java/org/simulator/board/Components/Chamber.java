package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Chamber extends Tile{
    public Chamber(int xCoordinate, int yCoordinate){
        super(StateType.CHAMBER);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();
    }
}
