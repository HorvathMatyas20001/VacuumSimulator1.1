package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Pipe extends Tile{
    public Pipe(int xCoordinate, int yCoordinate){
        super(StateType.PIPE);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();
    }
}
