package byow.Core.Structure;

import byow.Core.Direction;
import byow.Core.DrawTile;
import byow.Core.Position;
import byow.Core.Rect;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Corner extends Structure {

    public static final int WALL_WIDTH = 1;
    private final Direction directionH;
    private final int wayWidthH;
    private final Direction directionV;
    private final int wayWidthV;

    public Corner(Position pos, int wayWidth1, Direction direction1, int wayWidth2, Direction direction2) {
        super(createRect(pos, getWayWidthH(wayWidth1, direction1, wayWidth2, direction2), getWayWidthV(wayWidth1, direction1, wayWidth2, direction2)));
        this.directionH = getDirectionH(direction1, direction2);
        this.wayWidthH = getWayWidthH(wayWidth1, direction1, wayWidth2, direction2);
        this.directionV = getDirectionV(direction1, direction2);
        this.wayWidthV = getWayWidthV(wayWidth1, direction1, wayWidth2, direction2);
    }

    private static Rect createRect(Position pos, int wayWidthH, int wayWidthV) {
        return new Rect(pos, wayWidthV + 2 * WALL_WIDTH, wayWidthH + 2 * WALL_WIDTH);
    }

    private static Direction getDirectionH(Direction direction1, Direction direction2) {
        if (direction1 == Direction.Left || direction1 == Direction.Right) {
            return direction1;
        } else if (direction2 == Direction.Left || direction2 == Direction.Right) {
            return direction2;
        } else {
            throw new IllegalArgumentException("One of the given direction must be Direction.Left or Direction.Right");
        }
    }

    private static Direction getDirectionV(Direction direction1, Direction direction2) {
        if (direction1 == Direction.Up || direction1 == Direction.Down) {
            return direction1;
        } else if (direction2 == Direction.Up || direction2 == Direction.Down) {
            return direction2;
        } else {
            throw new IllegalArgumentException("One of the given direction must be Direction.Up or Direction.Down");
        }
    }

    private static int getWayWidthH(int wayWidth1, Direction direction1, int wayWidth2, Direction direction2) {
        if (direction1 == Direction.Left || direction1 == Direction.Right) {
            return wayWidth1;
        } else if (direction2 == Direction.Left || direction2 == Direction.Right) {
            return wayWidth2;
        } else {
            throw new IllegalArgumentException("One of the given direction must be Direction.Left or Direction.Right");
        }
    }

    private static int getWayWidthV(int wayWidth1, Direction direction1, int wayWidth2, Direction direction2) {
        if (direction1 == Direction.Up || direction1 == Direction.Down) {
            return wayWidth1;
        } else if (direction2 == Direction.Up || direction2 == Direction.Down) {
            return wayWidth2;
        } else {
            throw new IllegalArgumentException("One of the given direction must be Direction.Up or Direction.Down");
        }
    }

    @Override
    public void drawOn(TETile[][] world) {
        Rect rect = getRect();
        switch (directionH) {
            case Left:
                DrawTile.drawLineWithoutType(world, rect.getRightLine(), Tileset.WALL, Tileset.FLOOR);
                DrawTile.drawPointWithoutType(world, rect.getLeftUpPos(), Tileset.WALL, Tileset.FLOOR);
                DrawTile.drawPointWithoutType(world, rect.getLeftDownPos(), Tileset.WALL, Tileset.FLOOR);
                break;
            case Right:
                DrawTile.drawLineWithoutType(world, rect.getLeftLine(), Tileset.WALL, Tileset.FLOOR);
                DrawTile.drawPointWithoutType(world, rect.getLeftUpPos(), Tileset.WALL, Tileset.FLOOR);
                DrawTile.drawPointWithoutType(world, rect.getLeftDownPos(), Tileset.WALL, Tileset.FLOOR);
                break;
        }
        switch (directionV) {
            case Up:
                DrawTile.drawLineWithoutType(world, rect.getDownLine(), Tileset.WALL, Tileset.FLOOR);
                break;
            case Down:
                DrawTile.drawLineWithoutType(world, rect.getUpLine(), Tileset.WALL, Tileset.FLOOR);
                break;
        }
        DrawTile.drawRectWithoutType(world, rect, Tileset.FLOOR, Tileset.WALL);
    }
}
