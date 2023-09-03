package org.simulator.board.Components;

import org.simulator.board.StateType;

public class None extends Tile{
    public None(){
        super(StateType.NONE,StateType.NONE.getMaxConnections(),StateType.NONE.getMinConnections(),StateType.NONE.getColor());
        initializeConnection();
    }
    public void action(){

    }
}
