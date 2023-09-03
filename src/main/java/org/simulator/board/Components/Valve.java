package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Valve extends Tile{
    public Valve(){
        super(StateType.VALVE,StateType.VALVE.getMaxConnections(),StateType.VALVE.getMinConnections(),StateType.VALVE.getColor());
        initializeConnection();
    }
    public void action(){

    }
}
