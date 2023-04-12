package org.simulator.board;

import lombok.Getter;

import java.awt.*;

public enum StateType {
    EMPTY(0,0,false,Color.WHITE),
    CHAMBER(1,4,false,Color.CYAN),
    FORE_VACUUM_PUMP(1,1,true,Color.YELLOW),
    GAUGE(1,1,false,Color.MAGENTA),
    HV_PUMP(2,2,true,Color.PINK),
    PIPE(2,4,false,Color.LIGHT_GRAY),
    PUMP_STAND(1,1,true,Color.BLACK),
    VALVE(2,2,true,Color.DARK_GRAY),
    VENTING_VALVE(1,1,true,Color.ORANGE),
    NONE(0,0,false,null);
    @Getter
    private final int minConnections;
    @Getter
    private final int maxConnections;
    @Getter
    private final boolean activeElement;
    @Getter
    private final Color color;

    StateType(int minConnections, int maxConnections,boolean activeElement,Color color){
        this.minConnections = minConnections;
        this.maxConnections = maxConnections;
        this.activeElement = activeElement;
        this.color = color;
    }
}
