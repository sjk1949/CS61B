package byow.Core.Generator;

import byow.Core.Direction;
import byow.Core.Position;
import byow.Core.Structure.Hallway;
import byow.Core.Structure.Room;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class WorldGenerator {

    public static TETile[][] generate(WorldGenerationParameters mgp) {
        TETile[][] world = createEmptyWorld(mgp.width, mgp.height);
        fillWithTile(world, Tileset.GRASS);
        Room room = new Room(new Position(3, 4), 6, 4);
        Room room2 = new Room(new Position(2, 5), 10, 10);
        Hallway h1 = new Hallway(new Position(10, 12), 10, 3, Direction.Horizontal);
        room.drawOn(world);
        room2.drawOn(world);
        h1.drawOn(world);
        System.out.println(room2.collideWith(h1));
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
