package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Gauge extends Tile{
    public Gauge(int xCoordinate, int yCoordinate){
        super(StateType.GAUGE,StateType.GAUGE.getMinConnections(),StateType.GAUGE.getMaxConnections());
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnection();
    }
    public void action(){

    }
}
