package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;

    private static final int HEX_SIZE = 3;
    private static final int TESS_SIZE = 3;

    private static final long SEED = 12345;
    private static final Random RANDOM = new Random(SEED);

    /** Fill the world with Tileset.NOTHING. */
    private static void initializeTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    private static void generateWorld(TETile[][] tiles) {
        generateHexagonWorld(tiles, new Position(10, 6), HEX_SIZE, TESS_SIZE);
    }

    /**
     * Add a hexagon of the given size and type to the given location of the TETile[][] object.
     * @param tiles the given tiles world
     * @param position the left, down position of the Hexagon
     * @param size the size of the Hexagon
     * @param type the tile type of the Hexagon
     */
    private static void addHexagon(TETile[][] tiles, Position position, int size, TETile type) {
        Hexagon hexagon = new Hexagon(position, size, type);
        hexagon.drawOn(tiles);
    }

    /** The Random version of addHexagon(). */
    private static void addHexagon(TETile[][] tiles, Position position, int size, TETile type, Random random) {
        Hexagon hexagon = new Hexagon(position, size, type, random);
        hexagon.drawOn(tiles);
    }

    /** Add a column of hexagons to the tiles */
    private static void addHexagonColumn(TETile[][] tiles, Position position, int hexSize, int length) {
        for (int i = 0; i < length; i++) {
            addHexagon(tiles, position, hexSize, getRandomTile());
            position = Hexagon.getUpNeighborPosition(position, hexSize);
        }
    }

    /** Add a hexagon world. */
    private static void generateHexagonWorld(TETile[][] tiles, Position position, int hexSize, int tessSize) {
        for (int i = 0; i < tessSize - 1; i++) {
            addHexagonColumn(tiles, position, hexSize, tessSize + i);
            position = Hexagon.getRightDownNeighborPosition(position, hexSize);
        }

        for (int i = tessSize - 1; i >= 0; i--) {
            addHexagonColumn(tiles, position, hexSize, tessSize + i);
            position = Hexagon.getRightUpNeighborPosition(position, hexSize);
        }
    }

    private static TETile getRandomTile() {
        int tileNum = RANDOM.nextInt(5);
        return switch (tileNum) {
            case 0 -> Tileset.FLOWER;
            case 1 -> Tileset.WALL;
            case 2 -> Tileset.GRASS;
            case 3 -> Tileset.MOUNTAIN;
            case 4 -> Tileset.SAND;
            default -> Tileset.NOTHING;
        };
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeTiles(world);
        generateWorld(world);

        ter.renderFrame(world);
    }
}
