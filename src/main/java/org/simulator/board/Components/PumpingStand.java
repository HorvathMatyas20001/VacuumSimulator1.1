package org.simulator.board.Components;

import org.simulator.board.StateType;

public class PumpingStand extends ActiveTile{
    public PumpingStand(int xCoordinate, int yCoordinate){
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
