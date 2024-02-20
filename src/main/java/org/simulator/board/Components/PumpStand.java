package org.simulator.board.Components;

import lombok.Setter;
import org.simulator.board.StateType;

public class PumpStand extends ActiveTile{
    @Setter
    private boolean isActive;
    public PumpStand(int xCoordinate, int yCoordinate){
        super(StateType.PUMP_STAND);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();
    }

    @Override
    public void activate() {
        isActive = !isActive;
        //implementation needed.
    }
}
