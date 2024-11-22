package byow.Core.Structure;

import byow.Core.Position;
import byow.Core.Rect;
import byow.TileEngine.TETile;

public class Room extends Structure {

    public int width;
    public int height;

    public Room(Position position, int width, int height) {
        super(new Rect(position, width, height));
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean contains(Position pos) {
        return super.contains(pos);
    }

    @Override
    public void drawOn(TETile[][] world) {
        super.drawOn(world);
    }
}
