package byow.Core.InputDevice;

public interface InputDevice {
    /** Return the next char of the input, should secure that this is not null */
    public char getNextChar();
    /** Return true if the input is not over yet. */
    public boolean hasNextChar();
}
