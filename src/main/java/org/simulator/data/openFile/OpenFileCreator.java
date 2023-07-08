package org.simulator.data.openFile;

import org.simulator.controls.SimulatorLogic;
import org.simulator.controls.UniversalAdapter;

import java.awt.event.ActionEvent;

public class OpenFileCreator extends UniversalAdapter {
    SimulatorLogic logic;
    public OpenFileCreator(SimulatorLogic logic){
        this.logic = logic;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        new OpenFileWindow(logic);
    }
}

