package org.simulator.board;

import lombok.*;



public class Connection {
    @Getter
    private Tile tile;
    @Getter @Setter
    private boolean connected;
    @Getter @Setter
    private int order;

    public Connection(Tile tile){
        this.tile = tile;
        this.connected = false;
    }
}
