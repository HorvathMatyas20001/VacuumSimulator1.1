package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Valve extends Tile{
    public Valve(int xCoordinate, int yCoordinate){
        super(StateType.VALVE,StateType.VALVE.getMinConnections(),StateType.VALVE.getMaxConnections());
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnection();
    }
    public void action(){

    }
}
