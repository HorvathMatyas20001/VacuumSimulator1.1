package org.simulator.board.Components;

import org.simulator.board.StateType;

public class ForeVacuumPump extends Tile{
    public ForeVacuumPump(int xCoordinate, int yCoordinate){
        super(StateType.FORE_VACUUM_PUMP,StateType.FORE_VACUUM_PUMP.getMinConnections(),StateType.FORE_VACUUM_PUMP.getMaxConnections());
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnection();
    }
    public void action(){

    }
}
