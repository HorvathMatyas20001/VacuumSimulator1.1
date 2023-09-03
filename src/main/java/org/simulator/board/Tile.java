package org.simulator.board;

import org.simulator.controls.Mode;
import lombok.*;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Tile extends JPanel {
    @Getter @Setter
    private Map<Direction,Connection> neighbours;
    @Getter @Setter
    private StateType stateType;
    @Setter
    private boolean highlight;
    @Setter @Getter
    private boolean tooManyConnections;
    @Setter @Getter
    private boolean tooFewConnections;
    @Setter
    private Mode mode;

    public Tile(){
        this.stateType = StateType.EMPTY;
        this.setBackground(Color.WHITE);
        this.neighbours = new HashMap<>();
        this.mode = Mode.NONE;
        this.tooManyConnections = false;
        this.tooFewConnections = false;
    }
    public void addNeighbour(Direction direction, Tile tile){
        this.neighbours.put(direction,new Connection(tile));
    }

    public void removeNeighbour(Direction direction){
        this.neighbours.remove(direction);
    }

    public void connectBothWays(Direction direction){
        switch(direction){
            case UP:
                this.neighbours.get(direction).setConnected(true);
                this.neighbours.get(direction).getTile().getNeighbours().get(Direction.DOWN).setConnected(true);
                break;
            case DOWN:
                this.neighbours.get(direction).setConnected(true);
                this.neighbours.get(direction).getTile().getNeighbours().get(Direction.UP).setConnected(true);
                break;
            case LEFT:
                this.neighbours.get(direction).setConnected(true);
                this.neighbours.get(direction).getTile().getNeighbours().get(Direction.RIGHT).setConnected(true);
                break;
            case RIGHT:
                this.neighbours.get(direction).setConnected(true);
                this.neighbours.get(direction).getTile().getNeighbours().get(Direction.LEFT).setConnected(true);
                break;
        }
    }
    public void disconnectBothWays(Direction direction){
        switch(direction){
            case UP:
                this.neighbours.get(direction).setConnected(false);
                this.neighbours.get(direction).getTile().getNeighbours().get(Direction.DOWN).setConnected(false);
                break;
            case DOWN:
                this.neighbours.get(direction).setConnected(false);
                this.neighbours.get(direction).getTile().getNeighbours().get(Direction.UP).setConnected(false);
                break;
            case LEFT:
                this.neighbours.get(direction).setConnected(false);
                this.neighbours.get(direction).getTile().getNeighbours().get(Direction.RIGHT).setConnected(false);
                break;
            case RIGHT:
                this.neighbours.get(direction).setConnected(false);
                this.neighbours.get(direction).getTile().getNeighbours().get(Direction.LEFT).setConnected(false);
                break;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        if(this.neighbours.containsKey(Direction.UP)) {
            if (this.neighbours.get(Direction.UP).isConnected()) {
                g.fillRect((int) ((this.getWidth() * 0.5) - (this.getWidth() * 0.05)), 0, (int) (this.getWidth() * 0.1), (int) (this.getHeight() * 0.1));
            }
        }
        if(this.neighbours.containsKey(Direction.DOWN)) {
            if (this.neighbours.get(Direction.DOWN).isConnected()) {
                g.fillRect((int) ((this.getWidth() * 0.5) - (this.getWidth() * 0.05)), (int) (this.getHeight() - (this.getHeight() * 0.1)), (int) (this.getWidth() * 0.1), (int) (this.getHeight() * 0.1));
            }
        }
        if(this.neighbours.containsKey(Direction.LEFT)) {
            if (this.neighbours.get(Direction.LEFT).isConnected()) {
                g.fillRect(0, (int) ((this.getHeight() * 0.5) - (this.getHeight() * 0.05)), (int) (this.getWidth() * 0.1), (int) (this.getHeight() * 0.1));
            }
        }
        if(this.neighbours.containsKey(Direction.RIGHT)) {
            if (this.neighbours.get(Direction.RIGHT).isConnected()) {
                g.fillRect((int) (this.getWidth() - (this.getWidth() * 0.1)), (int) ((this.getHeight() * 0.5) - (this.getHeight() * 0.05)), (int) (this.getWidth() * 0.1), (int) (this.getHeight() * 0.1));
            }
        }
        g.setColor(this.stateType.getColor());

        switch(mode) {
            case DRAW_MODE:
                this.setBackground(Color.WHITE);
                break;
            case NONE:
                this.setBackground(Color.WHITE);
                break;
        }
        int tileWidth = (int) (this.getWidth() * 0.85);
        int tileHeight = (int) (this.getHeight() * 0.85);
        int tileX = (int) (this.getWidth() * 0.075);
        int tileY = (int) (this.getHeight() * 0.075);
        int cornerRadius = 30;

        g.fillRoundRect(tileX,tileY,tileWidth,tileHeight,cornerRadius,cornerRadius);
        g.setColor(Color.BLACK);
        g.drawRoundRect(tileX,tileY,tileWidth,tileHeight,cornerRadius,cornerRadius);

        if(tooManyConnections || tooFewConnections){
            g.setColor(Color.RED);

            // Calculate dimensions and position for the circle
            int circleDiameter = Math.min(tileWidth, tileHeight) / 4;
            int circleX = tileX + (tileWidth * 6/7)  - circleDiameter / 2;
            int circleY = tileY + tileHeight / 4 - circleDiameter / 2;

            // Draw red circle
            g.fillOval(circleX, circleY, circleDiameter, circleDiameter);
            g.setColor(Color.WHITE);
            g.drawOval(circleX, circleY, circleDiameter, circleDiameter);

            //C:\Users\mutyi\OneDrive\Desktop\5.json
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

    public int getNumberOfConnections() {
        int numberOfConnections = 0;
        for(Direction direction : Direction.values()){
            if(neighbours.containsKey(direction)){
                if(neighbours.get(direction).isConnected()) {
                    numberOfConnections++;
                }
            }
        }
        return numberOfConnections;
    }
}
