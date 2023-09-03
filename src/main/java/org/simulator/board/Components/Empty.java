package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Empty extends Tile{
    public Empty(){
        super(StateType.EMPTY,StateType.EMPTY.getMaxConnections(),StateType.EMPTY.getMinConnections(),StateType.EMPTY.getColor());
        initializeConnection();
    }

    public void action(){

    }
}
