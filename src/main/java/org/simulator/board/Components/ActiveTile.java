package org.simulator.board.Components;

import org.simulator.board.StateType;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public abstract class ActiveTile extends Tile implements MouseListener{
    protected boolean active;
    protected ActiveTile(StateType stateType) {
        super(stateType);
        addMouseListener(this);
    }
    public abstract void activate();
    @Override
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
        drawSwitchSign(g);

    }
    protected void drawSwitchSign(Graphics g) {
        int buttonWidth = 60; // Adjust as needed
        int buttonHeight = 30; // Adjust as needed
        int buttonX = getWidth() / 2 - buttonWidth / 2;
        int buttonY = getHeight() - buttonHeight - 5; // Place the button just below the error mark

        // Draw the button background
        g.setColor(Color.GRAY);
        g.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

        // Draw the button border
        g.setColor(Color.BLACK);
        g.drawRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

        // Draw the button text
        Font font = new Font("Arial", Font.BOLD, 12);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        String buttonText = active ? "On" : "Off"; // Text depends on the 'active' boolean
        int textWidth = metrics.stringWidth(buttonText);
        int textX = buttonX + buttonWidth / 2 - textWidth / 2;
        int textY = buttonY + buttonHeight / 2 + metrics.getAscent() / 2;
        g.setColor(Color.WHITE);
        g.drawString(buttonText, textX, textY);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int buttonWidth = 60; // Adjust as needed
        int buttonHeight = 30; // Adjust as needed
        int buttonX = getWidth() / 2 - buttonWidth / 2;
        int buttonY = getHeight() - buttonHeight - 5; // Place the button just below the error mark

        // Check if the mouse click occurred within the bounds of the switch sign button
        if (e.getX() >= buttonX && e.getX() <= buttonX + buttonWidth &&
                e.getY() >= buttonY && e.getY() <= buttonY + buttonHeight) {
            // Toggle the 'active' boolean
            active = !active;
            // Repaint the component to reflect the change
            repaint();
        }
        System.out.println("click");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Handle mouse pressed event
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle mouse released event
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Handle mouse entered event
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Handle mouse exited event
    }
}
