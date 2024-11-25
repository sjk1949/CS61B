package byow.Core.Generator;

import byow.Core.Direction;
import byow.Core.DrawTile;
import byow.Core.Line;
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
        HallwayGenerator.generate(world, roomManager, random);
        //Room room1 = new Room(new Position(5, 7), 5, 5);
        //Room room2 = new Room(new Position(15, 15), 5, 5);
        //room1.drawOn(world);
        //DrawTile.drawPoint(world, room1.getRect().getCenterPos(), Tileset.AVATAR);
        //room2.drawOn(world);
        //Line line1 = new Line(new Position(3, 4), new Position(3, 10));
        //Line line2 = new Line(new Position(10, 4), new Position(10, 10));
        //DrawTile.drawLine(world, line1, Tileset.WALL);
        //DrawTile.drawLine(world, line2, Tileset.WALL);
        //HallwayGenerator.createHallwayBetween(world, room1, room2, random);
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
