package byow.Core;

public class Rect {

    /** The left down position of the rectangle. */
    private final Position position;
    private final int width;
    private final int height;

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

    public Position getLeftDownPos() {
        return position;
    }

    public Position getLeftUpPos() {
        return position.shift(0, height() - 1);
    }

    public Position getRightDownPos() {
        return position.shift(width() - 1, 0);
    }

    public Position getRightUpPos() {
        return position.shift(width() - 1, height() - 1);
    }

    public Line getLeftLine() {
        return new Line(getLeftDownPos(), getLeftUpPos());
    }

    public Line getRightLine() {
        return new Line(getRightDownPos(), getRightUpPos());
    }

    public Line getDownLine() {
        return new Line(getLeftDownPos(), getRightDownPos());
    }

    public Line getUpLine() {
        return new Line(getLeftUpPos(), getRightUpPos());
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}
