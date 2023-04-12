package org.simulator.gui;

import org.simulator.board.StateType;
import org.simulator.controls.Mode;
import org.simulator.controls.SimulatorLogic;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ComponentButtons extends JButton implements ActionListener{
    private SimulatorLogic logic;
    private List<ComponentButtons> componentButtonsList;
    @Getter
    private StateType type;
    private String text;
    public ComponentButtons(SimulatorLogic logic, StateType type,List<ComponentButtons> componentButtonsList){
        super("");
        if(type == StateType.EMPTY){
            text = "REMOVE";
        }else{
            text = type.toString();
        }
        this.logic = logic;
        this.type = type;
        this.componentButtonsList = componentButtonsList;
        this.setFocusable(false);
        this.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.logic.getDrawType() != this.type){
            this.logic.setDrawType(this.type);
        }else{
            this.logic.setDrawType(StateType.NONE);
        }
        this.logic.changeMode(Mode.DRAW_MODE);
        onClick();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        g.setColor(type.getColor());
        g.fillRect(0, 0, w * 2 / 10, h);
        g.setColor(Color.BLACK);
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(text);
        int x = (w - textWidth) / 2;
        int y = (h - metrics.getHeight()) / 2 + metrics.getAscent();
        g.drawString(text, x, y);
    }
    public void onClick(){
        for(ComponentButtons buttons: componentButtonsList){
            if(buttons.getType() == this.logic.getDrawType()){
                buttons.getModel().setPressed(true);
                logic.getCheckConnectionsButton().getModel().setPressed(false);
            }else{
                buttons.getModel().setPressed(false);
            }
        }
    }
}