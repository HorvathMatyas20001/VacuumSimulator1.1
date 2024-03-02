package org.simulator.board.Components;

import org.simulator.board.StateType;

import java.awt.*;

public class Empty extends Tile{
    public Empty(int xCoordinate, int yCoordinate){
        super(StateType.EMPTY);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        initializeConnections();
    }
    @Override
    public void paintVacuumStateInfo(Graphics g, int XOffset, int YOffset, int tileWidth, int tileHeight){
        g.setColor(Color.WHITE);
        int cornerRadius = 30;
        g.fillRoundRect(XOffset,YOffset,tileWidth,tileHeight,cornerRadius,cornerRadius);
    }
}
