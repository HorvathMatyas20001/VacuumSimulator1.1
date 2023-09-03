package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Gauge extends Tile{
    public Gauge(){
        super(StateType.GAUGE,StateType.GAUGE.getMaxConnections(),StateType.GAUGE.getMinConnections(),StateType.GAUGE.getColor());
        initializeConnection();
    }
    public void action(){

    }
}
