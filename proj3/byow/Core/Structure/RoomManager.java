package byow.Core.Structure;

import byow.Core.Position;
import byow.Core.Utils.Graph;
import byow.Core.Utils.MST;

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

    public Graph<Room> createRoomGraph() {
        Graph<Room> graph = new Graph<>(rooms);
        for (Room room1 : rooms) {
            for (Room room2 : rooms) {
                if (room1 != room2) {
                    graph.addEdge(room1, room2, Position.mDist(room1.getPos(), room2.getPos()));
                }
            }
        }
        return graph;
    }

    public Graph<Room> getRoomGraphMST() {
        MST<Room> mst = new MST<>(createRoomGraph());
        return mst.findGraphMST();
    }

    @Override
    public Iterator<Room> iterator() {
        return rooms.iterator();
    }
}
