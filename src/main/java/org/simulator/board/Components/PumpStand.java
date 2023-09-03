package org.simulator.board.Components;

import org.simulator.board.StateType;

public class PumpStand extends Tile{
    public PumpStand(){
        super(StateType.PUMP_STAND,StateType.PUMP_STAND.getMaxConnections(),StateType.PUMP_STAND.getMinConnections(),StateType.PUMP_STAND.getColor());
        initializeConnection();
    }
    public void action(){

    }
}
