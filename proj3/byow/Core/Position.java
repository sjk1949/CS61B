package byow.Core;

import java.util.Objects;

public class Position {

    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position copy() {
        return new Position(x, y);
    }

    public static int dx(Position startPos, Position endPos) {
        return endPos.x -startPos.x;
    }

    public static int dy(Position startPos, Position endPos) {
        return endPos.y -startPos.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        Position pos = (Position) obj;
        return pos.x == x && pos.y == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[x: " + x + ", y: " + y +"]";
    }
}
