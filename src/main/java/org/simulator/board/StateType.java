package org.simulator.board;

import lombok.Getter;
import org.simulator.board.Components.*;

import java.awt.*;

public enum StateType {
    EMPTY(0,0,false,Color.WHITE, Empty.class),
    PIPE(2,4,false,Color.PINK, Pipe.class),
    CHAMBER(1,4,false,Color.CYAN, Chamber.class),
    GAUGE(1,1,false,Color.MAGENTA, Gauge.class),
    HV_PUMP(2,2,true,Color.LIGHT_GRAY, HVPump.class),
    PUMP_STAND(1,1,true,Color.DARK_GRAY, PumpStand.class),
    FORE_VACUUM_PUMP(1,1,true,Color.BLACK, ForeVacuumPump.class),
    VALVE(2,2,true,Color.YELLOW, Valve.class),
    VENTING_VALVE(1,1,true,Color.ORANGE, VentingValve.class),
    NONE(0,0,false,null, null);
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

    StateType(int minConnections, int maxConnections,boolean activeElement,Color color,Class<? extends Tile> tileClass){
        this.minConnections = minConnections;
        this.maxConnections = maxConnections;
        this.activeElement = activeElement;
        this.color = color;
        this.tileClass = tileClass;
    }

}
