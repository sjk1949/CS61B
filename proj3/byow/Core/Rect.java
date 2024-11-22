package byow.Core;

public class Rect {

    /** The left down position of the rectangle. */
    private Position position;
    public int width;
    public int height;

    public Rect(Position position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Rect(Position startPos, Position endPos) {
        this.position = new Position(Math.min(startPos.x, endPos.x), Math.min(startPos.y, endPos.y));
        this.width = Math.abs(Position.dx(startPos, endPos)) + 1;
        this.height = Math.abs(Position.dy(startPos, endPos)) + 1;
    }

    public boolean contains(Position pos) {
        return pos.x >= left() && pos.x <= right() &&
                pos.y >= down() && pos.y <= up();
    }

    public int left() {
        return position.x;
    }

    public int right() {
        return position.x + width - 1;
    }

    public int down() {
        return position.y;
    }

    public int up() {
        return position.y + height - 1;
    }
}
