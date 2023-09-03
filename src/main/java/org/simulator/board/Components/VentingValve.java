package org.simulator.board.Components;

import org.simulator.board.StateType;

public class VentingValve extends Tile{
    public VentingValve(){
        super(StateType.VENTING_VALVE,StateType.VENTING_VALVE.getMaxConnections(),StateType.VENTING_VALVE.getMinConnections(),StateType.VENTING_VALVE.getColor());
        initializeConnection();
    }
    public void action(){

    }
}
