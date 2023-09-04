package org.simulator.board.Components;

import org.simulator.board.StateType;

public class None extends Tile{
    public None(int xCoordinate, int yCoordinate){
        super(StateType.NONE,StateType.NONE.getMinConnections(),StateType.NONE.getMaxConnections());
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnection();
    }
    public void action(){

    }
}
