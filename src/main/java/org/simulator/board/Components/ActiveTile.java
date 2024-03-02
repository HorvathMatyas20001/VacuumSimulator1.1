package org.simulator.board.Components;

import lombok.Getter;
import lombok.Setter;
import org.simulator.board.StateType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;


public abstract class ActiveTile extends Tile{
    @Setter
    @Getter
    private boolean active;
    private Consumer<Boolean> stateChangeListener;
    private JButton switchButton;

    protected String activeStateText;
    protected String inactiveStateText;
    @Getter
    protected String currentStateText;
    protected ActiveTile(StateType stateType,String activeStateText, String inactiveStateText) {
        super(stateType);
        this.activeStateText = activeStateText;
        this.inactiveStateText = inactiveStateText;
        initializeSwitchButton();
        currentStateText = inactiveStateText;
        stateChangeListener = (state) -> {};
        setLayout(null);
    }
    public abstract void activate();
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        double scale = 0.85;
        double offsetScale = (1 - scale)/2;

        int tileWidth = (int) (this.getWidth() * scale);
        int tileHeight = (int) (this.getHeight() * scale);
        int XOffset = (int) (this.getWidth() * offsetScale);
        int YOffset = (int) (this.getHeight() * offsetScale);

        paintConnections(g, XOffset, YOffset, tileWidth, tileHeight);

        paintComponentBody(g, XOffset, YOffset, tileWidth, tileHeight);

        paintErrorMark(g, XOffset, YOffset, tileWidth, tileHeight);

        drawTextAndRectangle(g,XOffset,YOffset,30,30);

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
        switchButton.setFocusable(false);

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

        int buttonWidth = tileWidth/5;
        int buttonHeight = tileHeight/3;
        int buttonX = XOffset + (tileWidth/7) - buttonWidth/2;
        int buttonY = YOffset + (tileHeight * 5/7) - buttonHeight/2;

        switchButton.setSize(buttonWidth,buttonHeight);
        switchButton.setLocation(buttonX,buttonY);

        // Draw the button text
        Font font = new Font("Arial", Font.BOLD, 12);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        //String buttonText;

        if(active){
            g.setColor(Color.GREEN);
            g.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

            g.setColor(Color.BLACK);
            g2d.drawRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);
        }else{
            g.setColor(Color.RED);
            g.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);

            g.setColor(Color.BLACK);
            g2d.drawRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 10, 10);
        }
        int textWidth = metrics.stringWidth(currentStateText);
        int textX = buttonX + buttonWidth / 2 - textWidth / 2;
        int textY = buttonY + buttonHeight / 2 + metrics.getAscent() / 2;
        g.setColor(Color.WHITE);
        g.drawString(currentStateText, textX, textY);
    }

    protected void toggleActive() {
        active = !active;
        currentStateText = active ? activeStateText : inactiveStateText;
        stateChangeListener.accept(active);
        repaint();
    }
}
