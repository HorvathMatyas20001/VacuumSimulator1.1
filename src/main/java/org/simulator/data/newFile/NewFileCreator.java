package org.simulator.data.newFile;

import org.simulator.controls.SimulatorLogic;
import org.simulator.controls.UniversalAdapter;

import java.awt.event.ActionEvent;

public class NewFileCreator extends UniversalAdapter{
    private SimulatorLogic logic;
    public NewFileCreator(SimulatorLogic logic){
        this.logic = logic;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        new NewFileWindow(logic);
    }
}
