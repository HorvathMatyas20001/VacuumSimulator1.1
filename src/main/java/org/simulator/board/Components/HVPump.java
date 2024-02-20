package org.simulator.board.Components;

import lombok.Setter;
import org.simulator.board.StateType;

public class HVPump extends ActiveTile{
    @Setter
    private boolean isActive;
    public HVPump(int xCoordinate, int yCoordinate){
        super(StateType.HV_PUMP);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        isActive = false;
        initializeConnections();
    }
    @Override
    public void activate() {
        isActive = !isActive;
        //implementation needed.
    }
}
