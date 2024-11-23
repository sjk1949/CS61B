package byow.Core.Generator;

import byow.Core.Position;
import byow.Core.RandomUtils;
import byow.Core.Generator.GenerateUtils;
import byow.Core.Rect;
import byow.Core.Structure.Room;
import byow.Core.Structure.RoomManager;
import byow.TileEngine.TETile;

import java.util.Random;

public class RoomGenerator {

    /** Times try to generate rooms that don't collide with each other. */
    private static final int ROOM_GEN_TRY_NUM = 100;
    private static final int MAX_ROOM_WIDTH = 12;
    private static final int MIN_ROOM_WIDTH = 5;
    private static final int MAX_ROOM_HEIGHT = 12;
    private static final int MIN_ROOM_HEIGHT = 5;

    public static RoomManager generate(TETile[][] world, Random random) {
        RoomManager roomManager = new RoomManager();
        for (int i = 0; i < ROOM_GEN_TRY_NUM; i++) {
            Room room = createRandomRoom(world, random);
            if (!roomManager.collideWith(room)) {
                roomManager.add(room);
            }
        }
        for (Room room : roomManager) {
            room.drawOn(world);
        }
        return roomManager;
    }

    private static Room createRandomRoom(TETile[][] world, Random random) {
        Room room;
        Rect worldRect = GenerateUtils.getWorldRect(world);
        do {
            int width = RandomUtils.uniform(random, MIN_ROOM_WIDTH, MAX_ROOM_WIDTH);
            int height = RandomUtils.uniform(random, MIN_ROOM_HEIGHT, MAX_ROOM_HEIGHT);
            Position position = GenerateUtils.getRandomPos(world, random);
            room = new Room(position, width, height);
        } while (!worldRect.contains(room.getRect()));
        return room;
    }
}
