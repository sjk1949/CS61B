package byow.Core.Structure;

import byow.Core.Direction;
import byow.Core.DrawTile;
import byow.Core.Position;
import byow.Core.Rect;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Corner extends Structure {

    public static final int WALL_WIDTH = 1;
    private final Direction direction;

    public Corner(Position pos, int wayWidth, Direction direction) {
        super(createRect(pos, wayWidth));
        if (!(direction == Direction.LeftUp || direction == Direction.LeftDown || direction == Direction.RightUp || direction == Direction.RightDown)) {
            throw new IllegalArgumentException("direction must be Left(Right)Up(Down):" + direction);
        }
        this.direction = direction;
    }

    private static Rect createRect(Position pos, int wayWidth) {
        return new Rect(pos, wayWidth + 2 * WALL_WIDTH, wayWidth + 2 * WALL_WIDTH);
    }

    @Override
    public void drawOn(TETile[][] world) {
        Rect rect = getRect();
        DrawTile.drawRect(world, rect, Tileset.FLOOR);
        switch (direction) {
            case LeftUp:
                DrawTile.drawLine(world, rect.getDownLine(), Tileset.WALL);
                DrawTile.drawLine(world, rect.getRightLine(), Tileset.WALL);
                DrawTile.drawPoint(world, rect.getLeftUpPos(), Tileset.WALL);
                break;
            case LeftDown:
                DrawTile.drawLine(world, rect.getUpLine(), Tileset.WALL);
                DrawTile.drawLine(world, rect.getRightLine(), Tileset.WALL);
                DrawTile.drawPoint(world, rect.getLeftDownPos(), Tileset.WALL);
                break;
            case RightUp:
                DrawTile.drawLine(world, rect.getDownLine(), Tileset.WALL);
                DrawTile.drawLine(world, rect.getLeftLine(), Tileset.WALL);
                DrawTile.drawPoint(world, rect.getRightUpPos(), Tileset.WALL);
                break;
            case RightDown:
                DrawTile.drawLine(world, rect.getUpLine(), Tileset.WALL);
                DrawTile.drawLine(world, rect.getLeftLine(), Tileset.WALL);
                DrawTile.drawPoint(world, rect.getRightDownPos(), Tileset.WALL);
                break;
        }
    }
}
