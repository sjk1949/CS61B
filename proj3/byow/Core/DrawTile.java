package byow.Core;

import byow.TileEngine.TETile;

public class DrawTile {

    public static void drawPoint(TETile[][] tiles, Position pos, TETile tile) {
        tiles[pos.x][pos.y] = tile;
    }

    public static void drawLine(TETile[][] tiles, Line line, TETile tile) {
        for (Position pos : line) {
            drawPoint(tiles, pos, tile);
        }
    }

    public static void drawLine(TETile[][] tiles, Position pos1, Position pos2, TETile tile) {
        Line line = new Line(pos1, pos2);
        drawLine(tiles, line, tile);
    }
}
