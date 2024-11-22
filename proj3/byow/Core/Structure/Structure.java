package byow.Core.Structure;

import byow.Core.DrawTile;
import byow.Core.Line;
import byow.Core.Position;
import byow.Core.Rect;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Structure {

    private final Rect rect;

    public Structure(Rect rect) {
        this.rect = rect;
    }

    public int width() {
        return rect.width();
    }

    public int height() {
        return rect.height();
    }

    public Rect getRect() {
        return rect;
    }

    public boolean contains(Position pos) {
        return rect.contains(pos);
    }

    public void drawOn(TETile[][] world) {
        DrawTile.drawRect(world, rect, Tileset.NOTHING);
    }
}
