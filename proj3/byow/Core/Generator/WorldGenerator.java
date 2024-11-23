package byow.Core.Generator;

import byow.Core.Direction;
import byow.Core.Position;
import byow.Core.Structure.Hallway;
import byow.Core.Structure.Room;
import byow.Core.Structure.RoomManager;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class WorldGenerator {

    public static TETile[][] generate(WorldGenerationParameters mgp) {
        TETile[][] world = createEmptyWorld(mgp.width, mgp.height);
        Random random = new Random(mgp.seed);
        fillWithTile(world, Tileset.GRASS);
        RoomManager roomManager = RoomGenerator.generate(world, random);
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
