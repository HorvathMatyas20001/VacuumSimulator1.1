package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Gauge extends Tile{
    public Gauge(int xCoordinate, int yCoordinate){
        super(StateType.GAUGE);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();
    }
}
