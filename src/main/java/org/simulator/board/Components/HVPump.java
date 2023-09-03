package org.simulator.board.Components;

import org.simulator.board.StateType;

public class HVPump extends Tile{
    public HVPump(){
        super(StateType.HV_PUMP,StateType.HV_PUMP.getMaxConnections(),StateType.HV_PUMP.getMinConnections(),StateType.HV_PUMP.getColor());
        initializeConnection();
    }
    public void action(){

    }
}
