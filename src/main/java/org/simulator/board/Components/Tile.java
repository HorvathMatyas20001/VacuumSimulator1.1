package org.simulator.board.Components;

import lombok.Getter;
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
    protected int xCoordinate;
    @Getter
    protected int yCoordinate;

    protected Tile(StateType stateType) {
        this.stateType = stateType;
    }
    protected void initializeConnections() {
        connections = new EnumMap<>(Direction.class);
        for (Direction directions : Direction.values()) {
            connections.put(directions, false);
        }
    }
    private boolean tooManyConnectionCheck(){
        int connectionCounter = 0;
        for(Direction direction : Direction.values()){
            if(connections.get(direction)){
                connectionCounter++;
            }
        }
        return connectionCounter > this.stateType.getMaxConnections();
    }
    private boolean tooFewConnectionCheck(){
        int connectionCounter = 0;
        for(Direction direction : Direction.values()){
            if(connections.get(direction)){
                connectionCounter++;
            }
        }
        return connectionCounter < this.stateType.getMinConnections();
    }

    protected void paintConnections(Graphics g){
        g.setColor(Color.BLACK);
        if(this.connections.get(Direction.UP)) {
            g.fillRect((int) ((this.getWidth() * 0.5) - (this.getWidth() * 0.05)), 0, (int) (this.getWidth() * 0.1), (int) (this.getHeight() * 0.1));
        }
        if(this.connections.get(Direction.DOWN)) {
            g.fillRect((int) ((this.getWidth() * 0.5) - (this.getWidth() * 0.05)), (int) (this.getHeight() - (this.getHeight() * 0.1)), (int) (this.getWidth() * 0.1), (int) (this.getHeight() * 0.1));
        }
        if(this.connections.get(Direction.LEFT)) {
            g.fillRect(0, (int) ((this.getHeight() * 0.5) - (this.getHeight() * 0.05)), (int) (this.getWidth() * 0.1), (int) (this.getHeight() * 0.1));
        }
        if(this.connections.get(Direction.RIGHT)) {
            g.fillRect((int) (this.getWidth() - (this.getWidth() * 0.1)), (int) ((this.getHeight() * 0.5) - (this.getHeight() * 0.05)), (int) (this.getWidth() * 0.1), (int) (this.getHeight() * 0.1));
        }
    }
    protected void paintComponentBody(Graphics g ,int tileWidth, int tileHeight, int tileX, int tileY){
        g.setColor(this.stateType.getColor());
        int cornerRadius = 30;

        g.fillRoundRect(tileX,tileY,tileWidth,tileHeight,cornerRadius,cornerRadius);
        g.setColor(Color.BLACK);
        g.drawRoundRect(tileX,tileY,tileWidth,tileHeight,cornerRadius,cornerRadius);
    }
    protected void paintErrorMark(Graphics g ,int tileWidth, int tileHeight, int tileX, int tileY){
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
    protected void drawTextAndRectangle(Graphics g, int tileWidth, int tileHeight,int tileX, int tileY) {
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

        int tileWidth = (int) (this.getWidth() * 0.85);
        int tileHeight = (int) (this.getHeight() * 0.85);
        int XOffset = (int) (this.getWidth() * 0.075);
        int YOffset = (int) (this.getHeight() * 0.075);
        setBackground(Color.white);

        paintConnections(g);

        paintComponentBody(g, tileWidth, tileHeight, XOffset, YOffset);

        paintErrorMark(g, tileWidth, tileHeight, XOffset, YOffset);

        drawTextAndRectangle(g,30,30,XOffset,YOffset);
    }

    public void paintInfoPanelTile(Graphics g, int XOffset, int YOffset, int tileWidth, int tileHeight){
        g.setColor(this.getStateType().getColor());
        int cornerRadius = 30;

        g.fillRoundRect(XOffset,YOffset,tileWidth,tileHeight,cornerRadius,cornerRadius);
        g.setColor(Color.BLACK);
        g.drawRoundRect(XOffset,YOffset,tileWidth,tileHeight,cornerRadius,cornerRadius);
    }
    private void paintInfoPanelCenter(Graphics g, int XOffset, int YOffset, int tileWidth, int tileHeight){

    }
    private void paintInfoPanelConnections(Graphics g, int XOffset, int YOffset, int tileWidth, int tileHeight){
        if(this.connections.get(Direction.UP)) {
        }
        if(this.connections.get(Direction.DOWN)) {
        }
        if(this.connections.get(Direction.LEFT)) {
        }
        if(this.connections.get(Direction.RIGHT)) {
        }
    }

}
