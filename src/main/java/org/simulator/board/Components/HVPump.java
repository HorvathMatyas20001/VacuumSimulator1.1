package org.simulator.board.Components;

import org.simulator.board.StateType;

public class HVPump extends ActiveTile{
    public HVPump(int xCoordinate, int yCoordinate){
        super(StateType.HV_PUMP,"on","off");
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();
    }
    @Override
    protected void activate() {
    }
}
