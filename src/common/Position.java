package common;


import javafx.geometry.Pos;

import java.io.Serializable;
import java.util.Objects;

public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position distanceTo(Position destination) {
        int deltaX = Math.abs(this.getX() - destination.getX());
        int deltaY = Math.abs(this.getY() - destination.getY());
        return new Position(deltaX, deltaY);
    }
}
