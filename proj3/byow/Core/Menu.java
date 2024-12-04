package byow.Core;

import byow.Core.InputDevice.InputDevice;

public class Menu {

    public static long getSeed(InputDevice inputDevice) {
        long seed = 0;
        char c = inputDevice.getNextChar();
        while (c != 'S' && c != 's') {
            seed = seed * 10;
            seed += Character.getNumericValue(c);
            c = inputDevice.getNextChar();
        }
        return seed;
    }
}
