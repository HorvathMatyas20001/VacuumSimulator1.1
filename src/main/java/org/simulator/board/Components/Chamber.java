package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Chamber extends Tile{
    public Chamber(){
        super(StateType.CHAMBER,StateType.CHAMBER.getMaxConnections(),StateType.CHAMBER.getMinConnections(),StateType.CHAMBER.getColor());
        initializeConnection();
    }
    public void action(){

    }
}
