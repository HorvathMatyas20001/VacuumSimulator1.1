package org.simulator.board.Components;

import org.simulator.board.StateType;

public class ForeVacuumPump extends Tile{
    public ForeVacuumPump(){
        super(StateType.FORE_VACUUM_PUMP,StateType.FORE_VACUUM_PUMP.getMaxConnections(),StateType.FORE_VACUUM_PUMP.getMinConnections(),StateType.FORE_VACUUM_PUMP.getColor());
        initializeConnection();
    }
    public void action(){

    }
}
