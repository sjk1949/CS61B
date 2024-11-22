package byow.Core.Structure;

import byow.Core.Direction;
import byow.Core.DrawTile;
import byow.Core.Position;
import byow.Core.Rect;
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
        DrawTile.drawRect(world, rect, Tileset.FLOOR);
        if (direction == Direction.Horizontal) {
            DrawTile.drawLine(world, rect.getUpLine(), Tileset.WALL);
            DrawTile.drawLine(world, rect.getDownLine(), Tileset.WALL);
        } else {
            DrawTile.drawLine(world, rect.getLeftLine(), Tileset.WALL);
            DrawTile.drawLine(world, rect.getRightLine(), Tileset.WALL);
        }
    }
}
