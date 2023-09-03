package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Pipe extends Tile{
    public Pipe(){
        super(StateType.PIPE,StateType.PIPE.getMaxConnections(),StateType.PIPE.getMinConnections(),StateType.PIPE.getColor());
        initializeConnection();
    }
    public void action(){

    }
}
