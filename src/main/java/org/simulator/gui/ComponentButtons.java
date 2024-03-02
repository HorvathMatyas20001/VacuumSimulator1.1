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
    protected final Color backgroundColor = Color.GRAY;
    protected final Color buttonOutline = Color.DARK_GRAY;
    public ComponentButtons(SimulatorLogic logic, StateType type){
        super(type.getText());
        setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        this.logic = logic;
        this.type = type;
        this.setFocusable(false);
        this.addActionListener(this);
        this.setBorder(null);
        this.setBackground(backgroundColor);
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

        // Draw the background rounded rectangle
        drawRoundedRectangle(g, w, h);

        // Draw the text and the white rounded rectangle
        drawTextAndRectangle(g, w, h);
    }
    private void drawRoundedRectangle(Graphics g, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(6.0f));

        int newWidth = (int) (width * 0.9);
        int newHeight = (int) (height * 0.9);
        int x = (width - newWidth) / 2;
        int y = (height - newHeight) / 2;

        g.setColor(buttonOutline);

        if (activeToggle) {
            g.setColor(Color.GREEN);
            g2d.drawRoundRect(x, y, newWidth, newHeight, 30, 30);
            g.setColor(type.getColor().darker().darker());

        } else {
            g2d.drawRoundRect(x, y, newWidth, newHeight, 30, 30);
            g.setColor(type.getColor());

        }

        g2d.fillRoundRect(x, y, newWidth, newHeight, 30, 30);
    }

    private void drawTextAndRectangle(Graphics g, int width, int height) {
        Font font = new Font("Arial", Font.PLAIN, 12);
        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(type.getText());
        if(this.type.getText().equals("Empty")){
            textWidth = metrics.stringWidth("Remove");
        }

        int textX = (width - textWidth) / 2;
        int textY = (height - metrics.getHeight()) / 2 + metrics.getAscent();

        int whiteRectX = textX - 5;
        int whiteRectY = textY - metrics.getAscent() / 2 - 5;
        int whiteRectWidth = textWidth + 10;
        int whiteRectHeight = metrics.getHeight();

        if (activeToggle) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRoundRect(whiteRectX, whiteRectY, whiteRectWidth, whiteRectHeight, 5, 5);

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(buttonOutline);
        g2d.setStroke(new BasicStroke(2.0f));
        g.drawRoundRect(whiteRectX, whiteRectY, whiteRectWidth, whiteRectHeight, 5, 5);

        g.setColor(Color.BLACK);
        if(this.type.getText().equals("Empty")){
            g.drawString("Remove", textX, textY);
        }else{
            g.drawString(type.getText(), textX, textY);
        }
    }
}