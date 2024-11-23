package byow.Core.Structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoomManager implements Iterable<Room> {

    private final List<Room> rooms;

    public RoomManager() {
        this.rooms = new ArrayList<>();
    }

    public void add(Room room) {
        rooms.add(room);
    }

    public boolean contains(Room room) {
        return rooms.contains(room);
    }

    public boolean collideWith(Room other) {
        for (Room room : rooms) {
            if (room.collideWith(other)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Room> iterator() {
        return rooms.iterator();
    }
}
