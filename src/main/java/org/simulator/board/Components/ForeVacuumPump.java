package org.simulator.board.Components;

import org.simulator.board.StateType;

public class ForeVacuumPump extends ActiveTile{
    public ForeVacuumPump(int xCoordinate, int yCoordinate){
        super(StateType.FORE_VACUUM_PUMP);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();

    }
    @Override
    public void activate() {
    }
}
