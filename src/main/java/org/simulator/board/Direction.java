package org.simulator.board;

import lombok.Getter;

public enum Direction {
    UP(0,1),
    DOWN(0,-1),
    LEFT(-1,0),
    RIGHT(1,0);

    @Getter
    private final int x;
    @Getter
    private final int y;

    Direction(int x,int y){
        this.x = x;
        this.y = y;
    }
    public Direction getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                throw new IllegalArgumentException("Unsupported direction");
        }
    }
}
