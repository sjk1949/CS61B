package byow.Core.Generator;

import byow.Core.Position;
import byow.Core.Structure.Room;

import java.util.Random;

public class RoomGenerator {

    private Room createRandomRoom(Random random) {
        return new Room(new Position(1, 2), 1, 1);
    }
}
