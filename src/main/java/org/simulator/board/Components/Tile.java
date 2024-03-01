package org.simulator.board.Components;

import lombok.Getter;
import lombok.Setter;
import org.simulator.board.Direction;
import org.simulator.board.StateType;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

public abstract class Tile extends JPanel{
    @Getter
    protected EnumMap<Direction, Boolean> connections;
    @Getter
    protected final StateType stateType;
    @Getter
    @Setter
    protected int xCoordinate;
    @Getter
    @Setter
    protected int yCoordinate;
    protected boolean isVacuum;
    
    protected Tile(StateType stateType) {
        this.stateType = stateType;
    }
    protected void initializeConnections() {
        connections = new EnumMap<>(Direction.class);
        for (Direction directions : Direction.values()) {
            connections.put(directions, false);
        }
    }
    public boolean tooManyConnectionCheck(){
        int connectionCounter = 0;
        for(Direction direction : Direction.values()){
            if(connections.get(direction)){
                connectionCounter++;
            }
        }
        return connectionCounter > this.stateType.getMaxConnections();
    }
    public boolean tooFewConnectionCheck(){
        int connectionCounter = 0;
        for(Direction direction : Direction.values()){
            if(connections.get(direction)){
                connectionCounter++;
            }
        }
        return connectionCounter < this.stateType.getMinConnections();
    }

    protected void paintConnections(Graphics g, int XOffset, int YOffset ,int tileWidth, int tileHeight){
        Graphics2D g2d = (Graphics2D) g;
        Stroke originalStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(6.0f));
        g.setColor(Color.BLACK);
        if(this.connections.get(Direction.UP)) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(this.getWidth()/4,0,this.getWidth()/2,YOffset);
            g.setColor(Color.BLACK);
            g.drawLine(this.getWidth()/4,0,this.getWidth()/4,YOffset);
            g.drawLine(this.getWidth()*3/4,0,this.getWidth()*3/4,YOffset);
        }
        if(this.connections.get(Direction.DOWN)) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(this.getWidth()/4, YOffset + tileHeight,this.getWidth()/2,this.getHeight());
            g.setColor(Color.BLACK);
            g.drawLine(this.getWidth()/4,YOffset + tileHeight,this.getWidth()/4,this.getHeight());
            g.drawLine(this.getWidth()*3/4,YOffset + tileHeight,this.getWidth()*3/4,this.getHeight());
        }
        if(this.connections.get(Direction.LEFT)) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0,this.getHeight()/4,XOffset,this.getHeight()/2);
            g.setColor(Color.BLACK);
            g.drawLine(0,this.getHeight()/4,XOffset,this.getHeight()/4);
            g.drawLine(0,this.getHeight()* 3/4,XOffset,this.getHeight()* 3/4);
        }
        if(this.connections.get(Direction.RIGHT)) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(XOffset + tileWidth,this.getHeight()/4,this.getWidth(),this.getHeight()/2);
            g.setColor(Color.BLACK);
            g.drawLine(XOffset + tileWidth,this.getHeight()/4,this.getWidth(),this.getHeight()/4);
            g.drawLine(XOffset + tileWidth,this.getHeight()* 3/4,this.getWidth(),this.getHeight()* 3/4);
        }
        g2d.setStroke(originalStroke);
    }
    protected void paintComponentBody(Graphics g, int tileX, int tileY ,int tileWidth, int tileHeight){
        g.setColor(this.stateType.getColor());
        int cornerRadius = 30;

        g.fillRoundRect(tileX,tileY,tileWidth,tileHeight,cornerRadius,cornerRadius);
        g.setColor(Color.BLACK);
        g.drawRoundRect(tileX,tileY,tileWidth,tileHeight,cornerRadius,cornerRadius);
    }
    protected void paintErrorMark(Graphics g, int tileX, int tileY ,int tileWidth, int tileHeight){
        if(tooManyConnectionCheck() || tooFewConnectionCheck()){
            g.setColor(Color.RED);

            // Calculate dimensions and position for the circle
            int circleDiameter = Math.min(tileWidth, tileHeight) / 4;
            int circleX = tileX + (tileWidth * 6/7)  - circleDiameter / 2;
            int circleY = tileY + tileHeight / 4 - circleDiameter / 2;

            // Draw red circle
            g.fillOval(circleX, circleY, circleDiameter, circleDiameter);
            g.setColor(Color.WHITE);
            g.drawOval(circleX, circleY, circleDiameter, circleDiameter);

            int exclamationSize = circleDiameter * 2 / 3;
            Font exclamationFont = new Font("Arial", Font.BOLD, exclamationSize);

            g.setFont(exclamationFont);
            FontMetrics fontMetrics = g.getFontMetrics(exclamationFont);
            int exclamationWidth = fontMetrics.stringWidth("!");
            int exclamationHeight = fontMetrics.getHeight();

            int exclamationX = circleX + (circleDiameter / 2) - (exclamationWidth / 2);
            int exclamationY = circleY + (circleDiameter / 2) + (exclamationHeight /3);

            g.drawString("!", exclamationX, exclamationY);
        }
    }
    protected void drawTextAndRectangle(Graphics g,int tileX, int tileY, int tileWidth, int tileHeight) {
        Font font = new Font("Arial", Font.PLAIN, 12);
        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(this.stateType.getText());
        int textX = tileX + (tileWidth * 3/7) ;
        int textY = tileY + tileHeight / 4 ;

        int whiteRectX = textX - 5;
        int whiteRectY = textY - metrics.getAscent() / 2 - 5;
        int whiteRectWidth = textWidth + 10;
        int whiteRectHeight = metrics.getHeight();
        g.setColor(Color.WHITE);
        g.fillRoundRect(whiteRectX, whiteRectY, whiteRectWidth, whiteRectHeight, 5, 5);

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2.0f));
        g.drawRoundRect(whiteRectX, whiteRectY, whiteRectWidth, whiteRectHeight, 5, 5);

        g.drawString(this.stateType.getText(), textX, textY);
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        double scale = 0.85;
        double offsetScale = (1 - scale)/2;

        int tileWidth = (int) (this.getWidth() * scale);
        int tileHeight = (int) (this.getHeight() * scale);
        int XOffset = (int) (this.getWidth() * offsetScale);
        int YOffset = (int) (this.getHeight() * offsetScale);

        setBackground(Color.white);

        paintConnections(g, XOffset, YOffset, tileWidth, tileHeight);

        paintComponentBody(g, XOffset, YOffset, tileWidth, tileHeight);

        paintErrorMark(g, XOffset, YOffset, tileWidth, tileHeight);

        drawTextAndRectangle(g,XOffset,YOffset,30,30);
        //paintVacuumStateInfo(g, XOffset, YOffset, tileWidth/2, tileHeight/2);
    }

    public void paintInfoPanelTile(Graphics g, int XOffset, int YOffset, int tileWidth, int tileHeight){
        g.setColor(this.getStateType().getColor());
        int cornerRadius = 30;

        g.fillRoundRect(XOffset,YOffset,tileWidth,tileHeight,cornerRadius,cornerRadius);
        g.setColor(Color.BLACK);
        g.drawRoundRect(XOffset,YOffset,tileWidth,tileHeight,cornerRadius,cornerRadius);
    }

    public void paintVacuumStateInfo(Graphics g, int XOffset, int YOffset, int tileWidth, int tileHeight){
        Graphics2D g2d = (Graphics2D) g;
        Stroke originalStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(2.0f));

        g.setColor(Color.GRAY);
        g.fillRoundRect(XOffset + tileWidth/4,YOffset + tileHeight/4,tileWidth/2,tileHeight/2,10,10);

        g.setColor(Color.BLACK);
        g.drawRoundRect(XOffset + tileWidth/4,YOffset + tileHeight/4,tileWidth/2,tileHeight/2,10,10);

        int upTrueLeft = 2;
        int downTrueLeft = 2;
        float upTrueRight = 1f/2f;
        float downTrueRight = 1f/2f;

        if(this.connections.get(Direction.UP)) {
            upTrueLeft = 4;
            upTrueRight = 3f/4f;
        }
        if(this.connections.get(Direction.DOWN)){
            downTrueLeft = 4;
            downTrueRight = 3f/4f;
        }

        if(this.connections.get(Direction.UP)) {
            g.setColor(Color.GRAY);
            g.fillRect(XOffset + tileWidth/4,YOffset,tileWidth/2,tileHeight/2);
            g.setColor(Color.BLACK);
            g.drawLine(XOffset + tileWidth/4, YOffset, XOffset + tileWidth/4 ,YOffset + tileHeight/2);
            g.drawLine(XOffset + tileWidth*3/4, YOffset, XOffset + tileWidth*3/4 ,YOffset + tileHeight/2);
        }
        if(this.connections.get(Direction.DOWN)) {
            g.setColor(Color.GRAY);
            g.fillRect(XOffset + tileWidth/4,YOffset + tileHeight/2 ,tileWidth/2,tileHeight/2);
            g.setColor(Color.BLACK);
            g.drawLine(XOffset + tileWidth/4, YOffset+ tileHeight/2, XOffset + tileWidth/4 , YOffset + tileHeight);
            g.drawLine(XOffset + tileWidth *3/4, YOffset+ tileHeight/2, XOffset + tileWidth *3/4 , YOffset + tileHeight);
        }
        if(this.connections.get(Direction.LEFT)) {
            g.setColor(Color.GRAY);
            g.fillRect(XOffset,YOffset + tileHeight/4 ,tileWidth/2,tileHeight/2);
            g.setColor(Color.BLACK);
            g.drawLine(XOffset, YOffset+ tileHeight/4,
                    XOffset + tileWidth/upTrueLeft ,YOffset + tileHeight/4);

            g.drawLine(XOffset, YOffset+ tileHeight*3/4,
                    XOffset + tileWidth/downTrueLeft,YOffset + tileHeight*3/4);
        }
        if(this.connections.get(Direction.RIGHT)) {
            g.setColor(Color.GRAY);
            g.fillRect(XOffset + tileWidth/2,YOffset + tileHeight/4 ,tileWidth/2,tileHeight/2);
            g.setColor(Color.BLACK);
            g.drawLine(XOffset + (int)(tileWidth * upTrueRight), YOffset+ tileHeight/4,
                    XOffset + tileWidth ,YOffset + tileHeight/4);
            g.drawLine(XOffset + (int)(tileWidth * downTrueRight), YOffset+ tileHeight*3/4,
                    XOffset + tileWidth  ,YOffset + tileHeight*3/4);
        }
        g2d.setStroke(originalStroke);
    }
}
