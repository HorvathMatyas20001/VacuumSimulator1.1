package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Chamber extends Tile{
    public Chamber(int xCoordinate, int yCoordinate){
        super(StateType.CHAMBER,StateType.CHAMBER.getMinConnections(),StateType.CHAMBER.getMaxConnections());
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnection();
    }

    public void action(){

    }
}
