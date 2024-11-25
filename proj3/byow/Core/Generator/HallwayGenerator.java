package byow.Core.Generator;

import byow.Core.*;
import byow.Core.Structure.*;
import byow.Core.Utils.Graph;
import byow.TileEngine.TETile;

import java.util.Random;
import java.util.Set;

public class HallwayGenerator {

    public static void generate(TETile[][] world, RoomManager roomManager, Random random) {
        Graph<Room> roomGraphMST = roomManager.getRoomGraphMST();
        Set<Graph<Room>.Edge<Room>> edges = roomGraphMST.getEdgeSet();
        for (Graph<Room>.Edge<Room> edge : edges) {
            createHallwayBetween(world, edge.v1, edge.v2, random);
        }
    }

    public static void createHallwayBetween(TETile[][] world, Room room1, Room room2, Random random) {
        createHallwayBetween(world, room1.getRect().getCenterPos(), room2.getRect().getCenterPos(), random);
    }

    public static void createHallwayBetween(TETile[][] world, Line line1, Line line2, Random random) {}

    public static void createHallwayBetween(TETile[][] world, Position pos1, Position pos2, Random random) {
        if (pos1.x == pos2.x || pos1.y == pos2.y) {
            Hallway hallway = getHallwayFromLine(new Line(pos1, pos2), random);
            hallway.drawOn(world);
        } else {
            Structure[] structures = getLShapeHallway(pos1, pos2, random);
            for (Structure structure : structures) {
                structure.drawOn(world);
            }
        }
    }

    public static Hallway getHallwayFromLine(Line line, Random random) {
        int wayWidth = RandomUtils.uniform(random, 1, 3);
        if (line.getDirection() == Direction.Horizontal) {
            return new Hallway(line.getLeftPos().shift(0, -1), Math.abs(line.dx()), wayWidth, Direction.Horizontal);
        } else if (line.getDirection() == Direction.Vertical) {
            return new Hallway(line.getDownPos().shift(-1, 0), Math.abs(line.dy()), wayWidth, Direction.Vertical);
        }
        return null;
    }

    public static Structure[] getLShapeHallway(Position pos1, Position pos2, Random random) {
        Rect rect = new Rect(pos1, pos2);
        Line line1;
        Line line2;
        Position cornerPos;
        Direction cornerDir1;
        Direction cornerDir2;
        if (getSign(Position.dx(pos1, pos2)) == getSign(Position.dy(pos1, pos2))) {
            if (RandomUtils.uniform(random) < 0.5) {
                line1 = rect.getDownLine();
                line2 = rect.getRightLine();
                cornerPos = rect.getRightDownPos().shift(-1, -1);
                cornerDir1 = Direction.Left;
                cornerDir2 = Direction.Up;
            } else {
                line1 = rect.getLeftLine();
                line2 = rect.getUpLine();
                cornerPos = rect.getLeftUpPos().shift(-1, -1);
                cornerDir1 = Direction.Down;
                cornerDir2 = Direction.Right;
            }
        } else {
            if (RandomUtils.uniform(random) < 0.5) {
                line1 = rect.getDownLine();
                line2 = rect.getLeftLine();
                cornerPos = rect.getLeftDownPos().shift(-1, -1);
                cornerDir1 = Direction.Right;
                cornerDir2 = Direction.Up;
            } else {
                line1 = rect.getRightLine();
                line2 = rect.getUpLine();
                cornerPos = rect.getRightUpPos().shift(-1, -1);
                cornerDir1 = Direction.Down;
                cornerDir2 = Direction.Left;
            }
        }
        Hallway hallway1 = getHallwayFromLine(line1, random);
        Hallway hallway2 = getHallwayFromLine(line2, random);
        Corner corner = new Corner(cornerPos, hallway1.wayWidth, cornerDir1, hallway2.wayWidth, cornerDir2);
        return new Structure[]{hallway1, hallway2, corner};
    }

    /** if x>0, return 1, x=0, return 0, x<0, return -1. */
    private static int getSign(int x) {
        return Integer.compare(x, 0);
    }
}
