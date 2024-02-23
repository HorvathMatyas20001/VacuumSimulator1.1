package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Valve extends ActiveTile{
    public Valve(int xCoordinate, int yCoordinate){
        super(StateType.VALVE);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();
    }
    @Override
    public void activate() {
    }
}
