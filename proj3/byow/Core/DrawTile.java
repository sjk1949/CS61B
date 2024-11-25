package byow.Core;

import byow.TileEngine.TETile;

public class DrawTile {

    public static void drawPoint(TETile[][] tiles, Position pos, TETile tile) {
        tiles[pos.x][pos.y] = tile;
    }

    public static void drawPointWithoutType(TETile[][] tiles, Position pos, TETile tile, TETile type) {
        if (tiles[pos.x][pos.y] != type) {
            drawPoint(tiles, pos, tile);
        }
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

    public static void drawLineWithoutType(TETile[][] tiles, Line line, TETile tile, TETile type) {
        for (Position pos : line) {
            drawPointWithoutType(tiles, pos, tile, type);
        }
    }

    public static void drawRect(TETile[][] tiles, Rect rect, TETile tile) {
        for (int x = rect.left(); x <= rect.right(); x++) {
            for (int y = rect.down(); y <= rect.up(); y++) {
                drawPoint(tiles, new Position(x, y), tile);
            }
        }
    }

    public static void drawRectWithoutType(TETile[][] tiles, Rect rect, TETile tile, TETile type) {
        for (int x = rect.left(); x <= rect.right(); x++) {
            for (int y = rect.down(); y <= rect.up(); y++) {
                drawPointWithoutType(tiles, new Position(x, y), tile, type);
            }
        }
    }
}
