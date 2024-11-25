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

    public Position shift(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    public static Direction getDirection(Position basePos, Position targetPos) {
        int dx = dx(basePos, targetPos);
        int dy = dy(basePos, targetPos);

        // Determine direction based on dx and dy
        if (dx == 0 && dy > 0) {
            return Direction.Up;
        } else if (dx == 0 && dy < 0) {
            return Direction.Down;
        } else if (dy == 0 && dx > 0) {
            return Direction.Right;
        } else if (dy == 0 && dx < 0) {
            return Direction.Left;
        } else if (dx > 0 && dy > 0) {
            return Direction.RightUp;
        } else if (dx > 0 && dy < 0) {
            return Direction.RightDown;
        } else if (dx < 0 && dy > 0) {
            return Direction.LeftUp;
        } else if (dx < 0 && dy < 0) {
            return Direction.LeftDown;
        } else {
            throw new IllegalArgumentException("Base position and target position are the same.");
        }
    }

    public static int dx(Position startPos, Position endPos) {
        return endPos.x -startPos.x;
    }

    public static int dy(Position startPos, Position endPos) {
        return endPos.y -startPos.y;
    }

    public static int mDist(Position pos1, Position pos2) {
        return Math.abs(pos1.x - pos2.x) + Math.abs(pos1.y - pos2.y);
    }

    public static double rDist(Position pos1, Position pos2) {
        return Math.sqrt(dx(pos1, pos2) ^ 2 + dy(pos1, pos2) ^ 2);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() == Position.class) {
            Position pos = (Position) obj;
            return pos.x == x && pos.y == y;
        }
        return false;
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
