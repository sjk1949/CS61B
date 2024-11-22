package byow.Core.Structure;

import byow.Core.DrawTile;
import byow.Core.Position;
import byow.Core.Rect;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Structure {

    public Rect rect;

    public Structure(Rect rect) {
        this.rect = rect;
    }

    public boolean contains(Position pos) {
        return rect.contains(pos);
    }

    public void drawOn(TETile[][] world) {
        DrawTile.drawRect(world, rect, Tileset.NOTHING);
    }
}
