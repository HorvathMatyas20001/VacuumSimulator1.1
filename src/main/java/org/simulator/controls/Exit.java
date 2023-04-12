package org.simulator.controls;

import java.awt.event.ActionEvent;

public class Exit extends UniversalAdapter{
    @Override
    public void actionPerformed(ActionEvent e){
        System.exit(0);
    }

}
