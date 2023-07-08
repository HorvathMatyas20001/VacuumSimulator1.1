package org.simulator.board;

import lombok.*;



public class Connection {
    @Getter
    private Tile tile;
    @Getter @Setter
    private boolean connected;

    public Connection(Tile tile){
        this.tile = tile;
        this.connected = false;
    }
}
