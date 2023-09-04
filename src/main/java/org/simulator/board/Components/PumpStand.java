package org.simulator.board.Components;

import org.simulator.board.StateType;

public class PumpStand extends Tile{
    public PumpStand(int xCoordinate, int yCoordinate){
        super(StateType.PUMP_STAND,StateType.PUMP_STAND.getMinConnections(),StateType.PUMP_STAND.getMaxConnections());
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnection();
    }
    public void action(){

    }
}
