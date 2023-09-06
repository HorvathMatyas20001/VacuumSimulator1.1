package org.simulator.board;

import lombok.Getter;
import org.simulator.board.Components.*;

import java.awt.*;

public enum StateType {
    EMPTY(0,0,false,Color.WHITE, Empty.class,"Remove"),
    PIPE(2,4,false,Color.LIGHT_GRAY, Pipe.class,"Pipe"),
    CHAMBER(1,4,false,Color.GREEN, Chamber.class,"Chamber"),
    GAUGE(1,1,false,Color.MAGENTA, Gauge.class,"Gauge"),
    HV_PUMP(2,2,true,Color.YELLOW, HVPump.class,"H.V. Pump"),
    PUMP_STAND(1,1,true,Color.ORANGE, PumpStand.class,"Pump stand"),
    FORE_VACUUM_PUMP(1,1,true,Color.RED, ForeVacuumPump.class,"Fore vacuum pump"),
    VALVE(2,2,true,Color.CYAN, Valve.class,"Valve"),
    VENTING_VALVE(1,1,true,Color.BLUE, VentingValve.class,"Venting valve"),
    NONE(0,0,false,null, null,null);
    @Getter
    private final int minConnections;
    @Getter
    private final int maxConnections;
    @Getter
    private final boolean activeElement;
    @Getter
    private final Color color;
    @Getter
    private final Class<? extends Tile> tileClass;
    @Getter
    private final String text;

    StateType(int minConnections, int maxConnections,boolean activeElement,Color color,Class<? extends Tile> tileClass,String text){
        this.minConnections = minConnections;
        this.maxConnections = maxConnections;
        this.activeElement = activeElement;
        this.color = color;
        this.tileClass = tileClass;
        this.text = text;
    }

}
