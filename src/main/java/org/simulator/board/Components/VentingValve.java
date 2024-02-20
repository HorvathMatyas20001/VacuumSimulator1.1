package org.simulator.board.Components;

import lombok.Setter;
import org.simulator.board.StateType;

public class VentingValve extends ActiveTile{
    @Setter
    private boolean isActive;
    public VentingValve(int xCoordinate, int yCoordinate){
        super(StateType.VENTING_VALVE);
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
