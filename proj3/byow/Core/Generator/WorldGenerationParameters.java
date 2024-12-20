package byow.Core.Generator;

public class WorldGenerationParameters {

    // Default Parameters
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;
    public static final long SEED = 1234;

    public int width;
    public int height;
    public long seed;

    public WorldGenerationParameters(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.seed = seed;
    }

    public WorldGenerationParameters(long seed) {
        this.width = WIDTH;
        this.height = HEIGHT;
        this.seed = seed;
    }

    public static WorldGenerationParameters getDefalutParameters() {
        return new WorldGenerationParameters(WIDTH, HEIGHT, SEED);
    }
}
