package org.simulator.data.SaveFileAs;

import org.simulator.controls.SimulatorLogic;
import org.simulator.controls.UniversalAdapter;

import java.awt.event.ActionEvent;

public class SaveAsCreator extends UniversalAdapter {
    SimulatorLogic logic;
    public SaveAsCreator(SimulatorLogic logic){
        this.logic = logic;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        new SaveAsWindow(logic);
    }
}
