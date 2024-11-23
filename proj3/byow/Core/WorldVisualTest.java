package byow.Core;

import byow.Core.Generator.WorldGenerationParameters;
import byow.Core.Generator.WorldGenerator;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class WorldVisualTest {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    private static final long SEED  = 121;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = WorldGenerator.generate(new WorldGenerationParameters(WIDTH, HEIGHT, SEED));
        ter.renderFrame(world);
    }
}
