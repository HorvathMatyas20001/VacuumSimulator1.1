package org.simulator.gui;

import lombok.Getter;
import lombok.Setter;
import org.simulator.board.StateType;
import org.simulator.controls.Mode;
import org.simulator.controls.SimulatorLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ComponentButtons extends JButton implements ActionListener{
    private final SimulatorLogic logic;
    @Getter
    private final StateType type;

    int buttonWidth = 150;
    int buttonHeight = 40;
    @Setter
    private boolean activeToggle;
    public ComponentButtons(SimulatorLogic logic, StateType type){
        super(type.getText());
        setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        this.logic = logic;
        this.type = type;
        this.setFocusable(false);
        this.addActionListener(this);
        this.setBorder(null);
        this.setBackground(Color.LIGHT_GRAY);
        this.activeToggle = false;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.logic.getBoard() != null){
            if(this.logic.getDrawType() != this.type){
                this.logic.setDrawType(this.type);
                System.out.println("the draw type is :" + this.type);
            }else{
                this.logic.setDrawType(StateType.NONE);
            }
            this.logic.updateButtons();
            this.logic.changeMode(Mode.DRAW_MODE);
        }
    }
    public void updateButton(boolean state){
        this.activeToggle = state;
        this.repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();

        // Calculate the new width and height
        int newWidth = (int) (w * 0.95); // 5% smaller width
        int newHeight = (int) (h * 0.95); // 5% smaller height

        // Calculate the position to keep it centered
        int x = (w - newWidth) / 2;
        int y = (h - newHeight) / 2;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4.0f)); // Adjust the thickness as needed

        g.setColor(Color.GRAY);
        g2d.drawRoundRect(x, y, newWidth, newHeight, 30, 30);
        if(activeToggle){
            g.setColor(type.getColor().darker());
        }else{
            g.setColor(type.getColor());
        }
        g2d.fillRoundRect(x, y, newWidth, newHeight, 30, 30);

        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(type.getText());
        int textX = (w - textWidth) / 2;
        int textY = (h - metrics.getHeight()) / 2 + metrics.getAscent();

        // Calculate the position for the white rounded rectangle based on text position
        int whiteRectX = textX - 5; // Adjust as needed
        int whiteRectY = textY - metrics.getAscent() / 2 - 5; // Adjust as needed (subtract 5 pixels)
        int whiteRectWidth = textWidth + 10; // Adjust as needed
        int whiteRectHeight = metrics.getHeight(); // Use metrics height

        // Draw the white rounded rectangle
        if(activeToggle){
            g.setColor(Color.GREEN);
        }else{
            g.setColor(Color.WHITE);
        }
        g.fillRoundRect(whiteRectX, whiteRectY, whiteRectWidth, whiteRectHeight, 5, 5);

        g.setColor(Color.BLACK);
        g.drawString(type.getText(), textX, textY);


    }

}