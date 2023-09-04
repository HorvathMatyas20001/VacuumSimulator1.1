package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Pipe extends Tile{
    public Pipe(int xCoordinate, int yCoordinate){
        super(StateType.PIPE,StateType.PIPE.getMinConnections(),StateType.PIPE.getMaxConnections());
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnection();
    }
    public void action(){

    }
}
