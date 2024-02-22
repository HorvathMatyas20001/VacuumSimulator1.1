package org.simulator.board.Components;

import org.simulator.board.StateType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;


public abstract class ActiveTile extends Tile{

    public boolean active;
    private Consumer<Boolean> stateChangeListener;

    private JButton switchButton;
    protected ActiveTile(StateType stateType) {
        super(stateType);
        initializeSwitchButton();
        stateChangeListener = (state) -> {};
        setLayout(null);
    }
    public abstract void activate();
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        int XOffset = (int) (this.getWidth() * 0.075);
        int YOffset = (int) (this.getHeight() * 0.075);
        int tileWidth = (int) (this.getWidth() * 0.85);
        int tileHeight = (int) (this.getHeight() * 0.85);

        setBackground(Color.white);

        paintConnections(g);

        paintComponentBody(g, tileWidth, tileHeight, XOffset, YOffset);

        paintErrorMark(g, tileWidth, tileHeight, XOffset, YOffset);

        drawTextAndRectangle(g,30,30,XOffset,YOffset);

        drawButton(g,XOffset,YOffset,tileWidth,tileHeight);

    }
    public void setStateChangeListener(Consumer<Boolean> listener) {
        this.stateChangeListener = listener;
    }
    private void initializeSwitchButton() {

        switchButton = new JButton();
        switchButton.setOpaque(false);
        switchButton.setContentAreaFilled(false);
        switchButton.setBorderPainted(false);
        switchButton.setBounds(1, 1, 1, 1);
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleActive();
            }
        });
        add(switchButton);
    }
    public void drawButton(Graphics g, int XOffset, int YOffset, int tileWidth, int tileHeight){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(6.0f));

        int buttonWidth = tileWidth/6;
        int buttonHeight = tileHeight/4;
        int buttonX = XOffset + (tileWidth* 6/7) - buttonWidth/2;
        int buttonY = YOffset + (tileHeight * 5/7) - buttonHeight/2;

        switchButton.setSize(buttonWidth,buttonHeight);
        switchButton.setLocation(buttonX,buttonY);

        // Draw the button text
        Font font = new Font("Arial", Font.BOLD, 12);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        String buttonText;

        if(active){
            buttonText = "On";
            g.setColor(Color.GRAY.darker().darker());
            g.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

            g.setColor(Color.GREEN);
            g2d.drawRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);
        }else{
            buttonText = "Off";
            g.setColor(Color.GRAY);
            g.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

            g.setColor(Color.RED);
            g2d.drawRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);
        }
        int textWidth = metrics.stringWidth(buttonText);
        int textX = buttonX + buttonWidth / 2 - textWidth / 2;
        int textY = buttonY + buttonHeight / 2 + metrics.getAscent() / 2;
        g.setColor(Color.WHITE);
        g.drawString(buttonText, textX, textY);
    }

    protected void toggleActive() {
        active = !active;
        stateChangeListener.accept(active);
        repaint();
    }
}
