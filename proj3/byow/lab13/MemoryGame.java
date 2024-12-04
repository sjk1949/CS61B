package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        this.rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < n; i++) {
            string.append(CHARACTERS[RandomUtils.uniform(rand, 26)]);
        }
        return string.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text((double) this.width /2, (double) this.height /2, s);

        //TODO: If game is not over, display relevant game information at the top of the screen
        font = new Font("Monaco", Font.PLAIN, 20);
        StdDraw.setFont(font);
        StdDraw.line(0, height * 0.95, width, height * 0.95);
        StdDraw.textLeft(0, height * 0.975, " Round:" + round);
        if (playerTurn) {
            StdDraw.text((double) width /2, height * 0.975, "Type!");
        } else {
            StdDraw.text((double) width /2, height * 0.975, "Watch!");
        }
        String encourage = ENCOURAGEMENT[RandomUtils.uniform(rand, ENCOURAGEMENT.length)];
        StdDraw.textRight(width, height * 0.975, encourage + " ");

        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        int VISIBLE_TIME = 1000;
        int BLANK_TIME = 500;
        for (int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(i);
            drawFrame(String.valueOf(c));
            StdDraw.pause(VISIBLE_TIME);
            StdDraw.clear(Color.BLACK);
            drawFrame("");
            StdDraw.show();
            StdDraw.pause(BLANK_TIME);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        StringBuilder string = new StringBuilder();
        int i = 0;
        while (i < n) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                string.append(c);
                drawFrame(string.toString());
                i += 1;
            }
        }

        return string.toString();
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 0;
        gameOver = false;
        playerTurn = false;
        String answer;
        String request;

        do {
            round += 1;
            answer = generateRandomString(round);
            drawFrame("Round:" + round);
            flashSequence(answer);
            playerTurn = true;
            request = solicitNCharsInput(round);
            playerTurn = false;
            if (!request.equals(answer)) {
                gameOver = true;
            }
        } while (!gameOver);
        drawFrame("Game Over! You made it to round:" + round);

        //TODO: Establish Engine loop
    }

}
