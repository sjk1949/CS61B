package byow.Core;

public class Rect {

    /** The left down position of the rectangle. */
    public Position position;
    public int width;
    public int height;

    public Rect(Position position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public boolean contains(Position pos) {
        return pos.x >= position.x && pos.x <= position.x + width - 1 &&
                pos.y >= position.y && pos.y <= position.y + height - 1;
    }
}
