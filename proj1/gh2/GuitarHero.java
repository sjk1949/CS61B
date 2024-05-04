package gh2;

import deque.ArrayDeque;
import deque.Deque;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
    public static final int STRING_NUMBER = 37;
    public static final double CONCERT_A = 440.0;
    static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        /* create 37 guitar strings in a deque */
        GuitarString[] stringSet= new GuitarString[STRING_NUMBER];
        for (int i = 0; i < STRING_NUMBER; i++) {
            double frequency = CONCERT_A * Math.pow(2, (i - 24.0) / 12.0);
            stringSet[i] = new GuitarString(frequency);
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int keyNumber = keyboard.indexOf(key);
                if (keyNumber != -1) {
                    stringSet[keyNumber].pluck();
                }
            }

            double sample = 0;
            for (GuitarString string : stringSet) {
                sample += string.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            for (GuitarString string : stringSet) {
                string.tic();
            }
        }
    }
}
