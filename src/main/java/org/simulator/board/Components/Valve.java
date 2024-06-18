package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Valve extends MultiSectorTile{
    public Valve(int xCoordinate, int yCoordinate){
        super(StateType.VALVE,"open","closed");
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();
    }
    @Override
    protected void activate() {}
}
