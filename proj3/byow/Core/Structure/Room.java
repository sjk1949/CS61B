package byow.Core.Structure;

import byow.Core.DrawTile;
import byow.Core.Position;
import byow.Core.Rect;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Room extends Structure {

    public Room(Position position, int width, int height) {
        super(new Rect(position, width, height));
    }

    @Override
    public boolean contains(Position pos) {
        return super.contains(pos);
    }

    @Override
    public void drawOn(TETile[][] world) {
        DrawTile.drawRect(world, getRect(), Tileset.FLOOR);
        DrawTile.drawLine(world, getRect().getLeftLine(), Tileset.WALL);
        DrawTile.drawLine(world, getRect().getRightLine(), Tileset.WALL);
        DrawTile.drawLine(world, getRect().getDownLine(), Tileset.WALL);
        DrawTile.drawLine(world, getRect().getUpLine(), Tileset.WALL);
    }
}
