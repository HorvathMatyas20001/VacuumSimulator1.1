package org.simulator.board.Components;

import org.simulator.board.StateType;

public class VentingValve extends ActiveTile{
    public VentingValve(int xCoordinate, int yCoordinate){
        super(StateType.VENTING_VALVE,"open","closed");
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();
    }
    @Override
    protected void activate() {
    }
}
