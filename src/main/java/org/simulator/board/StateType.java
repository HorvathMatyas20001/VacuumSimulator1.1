package org.simulator.board;

import lombok.Getter;
import org.simulator.board.Components.*;

import java.awt.*;

public enum StateType {
    EMPTY(0,0,Color.WHITE, false, Empty.class,"Empty"),
    PIPE(2,4,new Color(127,255,0), false, Pipe.class,"Pipe"),
    CHAMBER(1,4,new Color(0,128,0), false, Chamber.class,"Chamber"),
    GAUGE(1,1,new Color(138,43,226), false, Gauge.class,"Gauge"),
    HV_PUMP(2,2,Color.YELLOW, true, HVPump.class,"H.V. Pump"),
    PUMP_STAND(1,1,Color.ORANGE, true, PumpStand.class,"Pump stand"),
    FORE_VACUUM_PUMP(1,1,new Color(191,105,0), true, ForeVacuumPump.class,"Fore vacuum pump"),
    VALVE(2,2,Color.CYAN, true, Valve.class,"Valve"),
    VENTING_VALVE(1,1,Color.BLUE, true, VentingValve.class,"Venting valve"),
    NONE(0,0,null, false,null,"None");
    @Getter
    private final int minConnections;
    @Getter
    private final int maxConnections;
    @Getter
    private final Color color;
    @Getter
    private final Class<? extends Tile> tileClass;
    @Getter
    private final boolean activeElement;
    @Getter
    private final String text;

    StateType(int minConnections, int maxConnections,Color color,boolean activeElement,Class<? extends Tile> tileClass,String text){
        this.minConnections = minConnections;
        this.maxConnections = maxConnections;
        this.color = color;
        this.activeElement = activeElement;
        this.tileClass = tileClass;
        this.text = text;
    }

}
