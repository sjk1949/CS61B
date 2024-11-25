package byow.Core.Structure;

import byow.Core.*;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Hallway extends Structure {

    public static final int WALL_WIDTH = 1;
    public int wayWidth;
    public int length;
    public Direction direction;

    public Hallway(Position pos, int length, int wayWidth, Direction direction) {
        super(createRect(pos, length, wayWidth, direction));
        this.wayWidth = wayWidth;
        this.length = length;
        this.direction = direction;
    }

    private static Rect createRect(Position pos, int length, int wayWidth, Direction direction) {
        int width;
        int height;
        if (!(direction == Direction.Horizontal || direction == Direction.Vertical)) {
            throw new IllegalArgumentException("direction must be Horizontal or Vertical: " + direction);
        }
        if (direction == Direction.Horizontal) {
            width = length;
            height = wayWidth + 2 * WALL_WIDTH;
        } else {
            width = wayWidth + 2 * WALL_WIDTH;
            height = length;
        }
        return new Rect(pos, width, height);
    }

    @Override
    public void drawOn(TETile[][] world) {
        Rect rect = getRect();
        if (direction == Direction.Horizontal) {
            DrawTile.drawLineWithoutType(world, rect.getDownLine(), Tileset.WALL, Tileset.FLOOR);
            for (Line line = rect.getDownLine().shift(0, 1); !line.equals(rect.getUpLine()); line = line.shift(0, 1)) {
                DrawTile.drawLine(world, line, Tileset.FLOOR);
            }
            DrawTile.drawLineWithoutType(world, rect.getUpLine(), Tileset.WALL, Tileset.FLOOR);
        } else {
            DrawTile.drawLineWithoutType(world, rect.getLeftLine(), Tileset.WALL, Tileset.FLOOR);
            for (Line line = rect.getLeftLine().shift(1, 0); !line.equals(rect.getRightLine()); line = line.shift(1, 0)) {
                DrawTile.drawLine(world, line, Tileset.FLOOR);
            }
            DrawTile.drawLineWithoutType(world, rect.getRightLine(), Tileset.WALL, Tileset.FLOOR);
        }
    }
}
