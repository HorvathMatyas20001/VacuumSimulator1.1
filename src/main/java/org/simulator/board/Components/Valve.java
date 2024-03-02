package org.simulator.board.Components;

import org.simulator.board.Direction;
import org.simulator.board.StateType;

import java.awt.*;

public class Valve extends ActiveTile{
    private boolean isTopVacuum;
    private boolean isBottomVacuum;

    private Direction topVacuumLocation;
    private Direction bottomVacuumLocation;
    public Valve(int xCoordinate, int yCoordinate){
        super(StateType.VALVE,"open","close");
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        isTopVacuum = false;
        isBottomVacuum = false;
        initializeConnections();
    }
    @Override
    public void paintVacuumStateInfo(Graphics g, int XOffset, int YOffset, int tileWidth, int tileHeight){
        if(!this.tooFewConnectionCheck() & !this.tooManyConnectionCheck()){
            updateVacuumSectors();
            Graphics2D g2d = (Graphics2D) g;
            Stroke originalStroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(2.0f));

            if((topVacuumLocation == Direction.UP) & (bottomVacuumLocation == Direction.DOWN)){
                drawSector(g,new int[]{XOffset + tileWidth/4, XOffset + tileWidth*3/4, XOffset + tileWidth*3/4, XOffset + tileWidth/4},
                        new int[]{YOffset, YOffset, YOffset + tileHeight/2, YOffset + tileHeight/2},4,isTopVacuum);
                drawSector(g,new int[]{XOffset + tileWidth/4, XOffset + tileWidth*3/4, XOffset + tileWidth*3/4, XOffset + tileWidth/4},
                        new int[]{YOffset + tileHeight/2, YOffset + tileHeight/2, YOffset + tileHeight, YOffset + tileHeight},4,isBottomVacuum);
            }else if((topVacuumLocation == Direction.LEFT) & (bottomVacuumLocation == Direction.RIGHT)){
                drawSector(g,new int[]{XOffset , XOffset + tileWidth/2, XOffset + tileWidth/2, XOffset},
                        new int[]{YOffset + tileHeight/4, YOffset + tileHeight/4, YOffset + tileHeight*3/4, YOffset + tileHeight*3/4},4,isTopVacuum);
                drawSector(g,new int[]{XOffset + tileWidth/2, XOffset + tileWidth, XOffset + tileWidth, XOffset + tileWidth/2},
                        new int[]{YOffset + tileHeight/4, YOffset + tileHeight/4, YOffset + tileHeight*3/4, YOffset + tileHeight*3/4},4,isBottomVacuum);
            }else if((topVacuumLocation == Direction.UP) & (bottomVacuumLocation == Direction.RIGHT)){
                drawSector(g,new int[]{XOffset + tileWidth/4, XOffset + tileWidth*3/4, XOffset + tileWidth*3/4, XOffset + tileWidth/4},
                        new int[]{YOffset, YOffset, YOffset + tileHeight/4, YOffset + tileHeight*3/4},4,isTopVacuum);
                drawSector(g,new int[]{XOffset + tileWidth * 3/4, XOffset + tileWidth, XOffset + tileWidth, XOffset + tileWidth/4},
                        new int[]{YOffset + tileHeight/4, YOffset + tileHeight/4, YOffset + tileHeight*3/4, YOffset + tileHeight*3/4},4,isBottomVacuum);
            }else if((topVacuumLocation == Direction.UP) & (bottomVacuumLocation == Direction.LEFT)) {
                drawSector(g,new int[]{XOffset + tileWidth/4, XOffset + tileWidth*3/4, XOffset + tileWidth*3/4, XOffset + tileWidth/4},
                        new int[]{YOffset, YOffset, YOffset + tileHeight*3/4, YOffset + tileHeight/4},4,isTopVacuum);
                drawSector(g,new int[]{XOffset , XOffset + tileWidth/4, XOffset + tileWidth * 3/4, XOffset},
                        new int[]{YOffset + tileHeight/4, YOffset + tileHeight/4, YOffset + tileHeight*3/4, YOffset + tileHeight*3/4},4,isBottomVacuum);
            }else if((topVacuumLocation == Direction.LEFT) & (bottomVacuumLocation == Direction.DOWN)){
                drawSector(g,new int[]{XOffset , XOffset + tileWidth * 3/4,XOffset + tileWidth/4, XOffset},
                        new int[]{YOffset + tileHeight/4, YOffset + tileHeight/4, YOffset + tileHeight*3/4, YOffset + tileHeight*3/4},4,isTopVacuum);
                drawSector(g,new int[]{XOffset + tileWidth/4, XOffset + tileWidth*3/4, XOffset + tileWidth*3/4, XOffset + tileWidth/4},
                        new int[]{YOffset + tileHeight * 3/4, YOffset + tileHeight/4, YOffset + tileHeight, YOffset + tileHeight},4,isBottomVacuum);
            }else if((topVacuumLocation == Direction.RIGHT) & (bottomVacuumLocation == Direction.DOWN)){
                drawSector(g,new int[]{XOffset + tileWidth/4, XOffset + tileWidth, XOffset + tileWidth, XOffset + tileWidth * 3/4},
                        new int[]{YOffset + tileHeight/4, YOffset + tileHeight/4, YOffset + tileHeight*3/4, YOffset + tileHeight*3/4},4,isTopVacuum);
                drawSector(g,new int[]{XOffset + tileWidth/4, XOffset + tileWidth*3/4, XOffset + tileWidth*3/4, XOffset + tileWidth/4},
                        new int[]{ YOffset + tileHeight/4,YOffset + tileHeight * 3/4, YOffset + tileHeight, YOffset + tileHeight},4,isBottomVacuum);
            }
            g2d.setStroke(originalStroke);
        }else{
            super.paintVacuumStateInfo(g,XOffset,YOffset,tileWidth,tileHeight);
        }
    }
    @Override
    protected void activate() {
    }
    @Override
    protected void drawVacuumState(Graphics g, int XOffset, int YOffset, int tileWidth, int tileHeight){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4.0f));

        int buttonWidth = tileWidth/5;
        int buttonHeight = tileHeight/3;
        int buttonX = XOffset + (tileWidth/7) - buttonWidth/2;
        int buttonY = YOffset + (tileHeight * 5/7) - buttonHeight/2;

        //Draw top box
        drawVacuumStateSection(g,buttonX, buttonY, buttonWidth, buttonHeight/2,isTopVacuum);

        //Draw bottom box;
        drawVacuumStateSection(g,buttonX, buttonY + buttonHeight/2, buttonWidth, buttonHeight/2,isBottomVacuum);

        g.setColor(Color.BLACK);
        g2d.drawRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

        //Draw box divide
        g.drawLine(buttonX,buttonY + buttonHeight/2,buttonX + buttonWidth,buttonY + buttonHeight/2);
    }
    private void drawVacuumStateSection(Graphics g, int XOffset, int YOffset, int tileWidth, int tileHeight,Boolean isVacuum){
        g.setColor(isVacuum ? vacuumColor : airColor);
        g.fillRoundRect(XOffset, YOffset, tileWidth, tileHeight, 10, 10);
        g.setColor(Color.WHITE);

        //Change font
        Font font = new Font("Arial", Font.BOLD, 12);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();

        int textWidth = metrics.stringWidth(isVacuum ? "Vacuum" : "Air");
        int textX = XOffset + tileWidth / 2 - textWidth / 2;
        int textY = YOffset + tileHeight / 2 + metrics.getAscent() / 2;
        g.setColor(Color.WHITE);
        g.drawString(isVacuum ? "Vacuum" : "Air", textX, textY);
    }
    private void updateVacuumSectors(){
        topVacuumLocation = null;
        bottomVacuumLocation = null;
        if(!this.tooFewConnectionCheck() & !this.tooManyConnectionCheck()){
            if(this.connections.get(Direction.UP)) {
                topVacuumLocation = Direction.UP;
            }
            if(this.connections.get(Direction.DOWN)) {
                bottomVacuumLocation = Direction.DOWN;
            }
            if(this.connections.get(Direction.LEFT)) {
                if(!this.connections.get(Direction.UP)){
                    topVacuumLocation = Direction.LEFT;
                }else{
                    bottomVacuumLocation = Direction.LEFT;
                }
            }
            if(this.connections.get(Direction.RIGHT)) {
                if(!this.connections.get(Direction.DOWN)){
                    bottomVacuumLocation = Direction.RIGHT;
                }else{
                    topVacuumLocation = Direction.RIGHT;
                }
            }
        }
    }
    private void drawSector(Graphics g,int[]x,int[]y,int arraySize,boolean vacuumState){
        g.setColor(vacuumState ? vacuumColor : airColor);
        g.fillPolygon(x,y,arraySize);
        g.setColor(Color.BLACK);
        g.drawPolygon(x,y,arraySize);
    }
}
