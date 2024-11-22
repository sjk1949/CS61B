package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import byow.Core.DrawTile;

public class WorldGenerator {

    public static TETile[][] generate(WorldGenerationParameters mgp) {
        TETile[][] world = createEmptyWorld(mgp.width, mgp.height);
        fillWithTile(world, Tileset.GRASS);
        DrawTile.drawLine(world, new Position(3,4), new Position(3,4), Tileset.WALL);
        return world;
    }

    private static TETile[][] createEmptyWorld(int width, int height) {
        TETile[][] world = new TETile[width][height];
        fillWithTile(world, Tileset.NOTHING);
        return world;
    }

    private static void fillWithTile(TETile[][] world, TETile tile) {
        int width = world.length;
        int height = world[0].length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world[x][y] = tile;
            }
        }
    }
}
