package org.simulator.gui;

import org.simulator.controls.Mode;
import org.simulator.controls.SimulatorLogic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckConnections extends JButton implements ActionListener {
    private SimulatorLogic logic;
    public CheckConnections (SimulatorLogic logic){
        super("CheckConnections");
        this.logic = logic;

        this.setFocusable(false);
        this.addActionListener(this);
    }

    private void setFlagForIncorrectConnections(){
        for(int i = 0; i < logic.getXDimension(); i++){
            for(int j = 0; j < logic.getYDimension(); j++){
                if(logic.getBoard().getBoard()[i][j].getStateType().getMaxConnections() < logic.getBoard().getBoard()[i][j].getNumberOfConnections()){
                    logic.getBoard().getBoard()[i][j].setTooManyConnections(true);
                    logic.getBoard().getBoard()[i][j].setTooFewConnections(false);
                }else if(logic.getBoard().getBoard()[i][j].getStateType().getMinConnections() > logic.getBoard().getBoard()[i][j].getNumberOfConnections()){
                    logic.getBoard().getBoard()[i][j].setTooFewConnections(true);
                    logic.getBoard().getBoard()[i][j].setTooManyConnections(false);

                }else{
                    logic.getBoard().getBoard()[i][j].setTooManyConnections(false);
                    logic.getBoard().getBoard()[i][j].setTooFewConnections(false);
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(this.logic.getMode() != Mode.CHECK_CONNECTION_MODE){
            this.logic.changeMode(Mode.CHECK_CONNECTION_MODE);
            setFlagForIncorrectConnections();
            this.getModel().setPressed(true);
            for(ComponentButtons buttons : this.logic.getComponentButtonsList()){
                buttons.getModel().setPressed(false);
            }
        }else{
            this.logic.changeMode(Mode.NONE);
            this.getModel().setPressed(false);
        }

    }
}
