package byow.lab12;

import byow.TileEngine.TETile;

import java.util.Random;

public class Hexagon {

    public Position position;
    public int size;
    public TETile tile;
    private Random random;

    public Hexagon(Position position, int size, TETile tile) {
        this.position = position;
        this.size = size;
        this.tile = tile;
    }

    public Hexagon(Position position, int size, TETile tile, Random random) {
        this.position = position;
        this.size = size;
        this.tile = tile;
        this.random = random;
    }

    public static Position getUpNeighborPosition(Position position, int size) {
        return position.shift(0, 2 * size);
    }

    public static Position getRightUpNeighborPosition(Position position, int size) {
        return position.shift(2 * size - 1, size);
    }

    public static Position getRightDownNeighborPosition(Position position, int size) {
        return position.shift(2 * size - 1, - size);
    }

    /**
     * Draw the hexagon to the given TETile[][] object.
     */
    public void drawOn(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;

        for (int i = 0; i < 2 * size; i++) {
            for(int x = startXOfYLayer(i); x <= endXOfYLayer(i); x++) {
                int y = position.y + i;
                if (x >= 0 && x < width && y >= 0 && y < height) { // The position must in the border.
                    tiles[x][y] = getTile();
                }
            }
        }
    }

    private TETile getTile() {
        if (random != null) {
            return TETile.colorVariant(tile, 30, 30, 30, random);
        } else {
            return tile;
        }
    }

    /**
     * Return the num of tiles of the given Y layer start from the left, down position.
     * @param y the y layer of the hexagon, the range is in [0, 2 * size - 1]
     */
    private int lengthOfYLayer(int y) {
        if (y < 0 || y >= 2 * size) {
            return 0;
        }
        if (y < size) {
            return size + 2 * y;
        } else {
            return size + 2 * (2 * size - 1 - y);
        }
    }

    /**
     * Start X of the given layer Y.
     * @return the start X in the given tiles
     */
    private int startXOfYLayer(int y) {
        int startX;
        if (y < size) {
            startX = position.x - y;
        } else {
            startX = position.x - (2 * size - 1 - y);
        }
        return startX;
    }

    /**
     * @return the end X of the Layer Y, [X, Y] is included in the hexagon
     */
    private int endXOfYLayer(int y) {
        return startXOfYLayer(y) + lengthOfYLayer(y) - 1;
    }
}
