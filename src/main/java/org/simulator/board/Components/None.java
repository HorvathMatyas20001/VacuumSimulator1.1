package org.simulator.board.Components;

import org.simulator.board.StateType;

import java.awt.*;

public class None extends Tile{
    public None(int xCoordinate, int yCoordinate){
        super(StateType.NONE);
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
