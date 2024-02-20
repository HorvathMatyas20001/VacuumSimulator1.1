package org.simulator.board.Components;

import org.simulator.board.StateType;

public class None extends Tile{
    public None(int xCoordinate, int yCoordinate){
        super(StateType.NONE);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();
    }
}
