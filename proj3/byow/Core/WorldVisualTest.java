package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class WorldVisualTest {

    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = WorldGenerator.generate(WorldGenerationParameters.getDefalutParameters());
        ter.renderFrame(world);
    }
}
