package org.simulator.board.Components;

import lombok.Setter;
import org.simulator.board.StateType;

public class Valve extends ActiveTile{
    @Setter
    private boolean isActive;
    public Valve(int xCoordinate, int yCoordinate){
        super(StateType.VALVE);
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
