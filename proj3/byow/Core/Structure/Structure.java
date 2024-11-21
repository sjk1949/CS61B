package byow.Core.Structure;

import byow.Core.Position;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Structure {

    public Position position;

    public Structure(Position position) {
        this.position = position;
    }

    public boolean contains(Position pos) {
        return pos.equals(position);
    }

    public void drawOn(TETile[][] world) {
        world[position.x][position.y] = Tileset.NOTHING;
    }
}
