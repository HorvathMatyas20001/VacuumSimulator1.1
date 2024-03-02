package org.simulator.board.Components;

import org.simulator.board.StateType;

public class Valve extends ActiveTile{
    protected String activeStateText = "on";
    protected String inactiveStateText = "off";
    protected String currentStateText;
    public Valve(int xCoordinate, int yCoordinate){
        super(StateType.VALVE,"open","close");
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();
    }
    @Override
    public void activate() {
    }
}
