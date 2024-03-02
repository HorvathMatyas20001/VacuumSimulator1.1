package org.simulator.board.Components;

import org.simulator.board.StateType;

public class PumpStand extends ActiveTile{
    public PumpStand(int xCoordinate, int yCoordinate){
        super(StateType.PUMP_STAND,"on","off");
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();
    }
    @Override
    protected void activate() {
        VacuumAirStateChanger(!isVacuum);
    }
}
