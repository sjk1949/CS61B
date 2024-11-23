package byow.Core.Structure;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class StructureManager {

    public final RoomManager roomManager;
    private final Set<Hallway> hallways;
    private final Set<Corner> corners;

    public StructureManager() {
        this.roomManager = new RoomManager();
        this.hallways = new HashSet<>();
        this.corners = new HashSet<>();
    }

    public void add(Structure structure) {
        if (structure.getClass() == Room.class) {
            roomManager.add((Room) structure);
        } else if (structure.getClass() == Hallway.class) {
            hallways.add((Hallway) structure);
        } else if (structure.getClass() == Corner.class) {
            corners.add((Corner) structure);
        }
    }
}
