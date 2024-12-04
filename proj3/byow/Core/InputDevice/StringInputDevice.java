package byow.Core.InputDevice;

public class StringInputDevice implements InputDevice {

    private String input;
    private int index = 0;

    public StringInputDevice(String string) {
        this.input = string;
    }

    @Override
    public char getNextChar() {
            char result = input.charAt(index);
            index += 1;
            return result;
    }

    @Override
    public boolean hasNextChar() {
        return index < input.length();
    }
}
