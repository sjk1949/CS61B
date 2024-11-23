package byow.Core.Generator;

import byow.Core.Position;
import byow.Core.RandomUtils;
import byow.Core.Rect;
import byow.TileEngine.TETile;

import java.util.Random;

public class GenerateUtils {

    public static Rect getWorldRect(TETile[][] world) {
        int width = world.length;
        int height = world[0].length;
        return new Rect(new Position(0, 0), width, height);
    }

    public static Position getRandomPos(TETile[][] world, Random random) {
        int width = world.length;
        int height = world[0].length;
        int x = RandomUtils.uniform(random, width);
        int y = RandomUtils.uniform(random, height);
        return new Position(x, y);
    }
}
