package org.simulator.board.Components;

import org.simulator.board.StateType;

public class VentingValve extends Tile{
    public VentingValve(int xCoordinate, int yCoordinate){
        super(StateType.VENTING_VALVE,StateType.VENTING_VALVE.getMinConnections(),StateType.VENTING_VALVE.getMaxConnections());
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnection();
    }
    public void action(){

    }
}
