package org.simulator.board.Components;

import org.simulator.board.StateType;

public class HVPump extends Tile{
    public HVPump(int xCoordinate, int yCoordinate){
        super(StateType.HV_PUMP,StateType.HV_PUMP.getMinConnections(),StateType.HV_PUMP.getMaxConnections());
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnection();
    }
    public void action(){

    }
}
