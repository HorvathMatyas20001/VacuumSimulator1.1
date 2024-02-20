package org.simulator.board;

import lombok.Getter;
import org.simulator.board.Components.*;

import java.awt.*;

public enum StateType {
    EMPTY(0,0,Color.WHITE, Empty.class,"Empty"),
    PIPE(2,4,Color.LIGHT_GRAY, Pipe.class,"Pipe"),
    CHAMBER(1,4,Color.GREEN, Chamber.class,"Chamber"),
    GAUGE(1,1,Color.MAGENTA, Gauge.class,"Gauge"),
    HV_PUMP(2,2,Color.YELLOW, HVPump.class,"H.V. Pump"),
    PUMP_STAND(1,1,Color.ORANGE, PumpStand.class,"Pump stand"),
    FORE_VACUUM_PUMP(1,1,Color.RED, ForeVacuumPump.class,"Fore vacuum pump"),
    VALVE(2,2,Color.CYAN, Valve.class,"Valve"),
    VENTING_VALVE(1,1,Color.BLUE, VentingValve.class,"Venting valve"),
    NONE(0,0,null, null,null);
    @Getter
    private final int minConnections;
    @Getter
    private final int maxConnections;
    @Getter
    private final Color color;
    @Getter
    private final Class<? extends Tile> tileClass;
    @Getter
    private final String text;

    StateType(int minConnections, int maxConnections,Color color,Class<? extends Tile> tileClass,String text){
        this.minConnections = minConnections;
        this.maxConnections = maxConnections;
        this.color = color;
        this.tileClass = tileClass;
        this.text = text;
    }

}
