package byow.Core;

import java.util.Iterator;

public class Line implements Iterable<Position> {

    public final Position startPos;
    public final Position endPos;

    public Line(Position startPos, Position endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    private int getY(int x) {
        return rint((float) dy() / dx() * (x - startPos.x) + startPos.y);
    }

    private int getX(int y) {
        return rint((float) dx() / dy() * (y - startPos.y) + startPos.x);
    }

    private int dx() {
        return Position.dx(startPos, endPos);
    }

    private int dy() {
        return Position.dy(startPos, endPos);
    }

    private boolean isSteep() {
        return Math.abs(dx()) < Math.abs(dy());
    }

    public boolean contains(Position pos) {
        int dx = Math.abs(dx());
        int dy = Math.abs(dy());
        if (dx == 0 && dy == 0) {
            return pos.equals(startPos);
        }
        // The pos should in range [x1, x2], [y1, y2].
        Rect rect = new Rect(startPos, endPos);
        if (!rect.contains(pos)) {
            return false;
        }
        if (!isSteep()) {
            return pos.y == getY(pos.x);
        } else {
            return pos.x == getX(pos.y);
        }
    }

    @Override
    public Iterator<Position> iterator() {
        return new PosIterator();
    }

    private class PosIterator implements Iterator<Position> {

        private final Position nextPos = startPos.copy();
        /** The direction x|y variate, if the line is steep, choose y, else choose x */
        private final int sign = isSteep() ? getSign(dy()) : getSign(dx());

        @Override
        public boolean hasNext() {
            return Line.this.contains(nextPos);
        }

        @Override
        public Position next() {
            Position currPos = nextPos.copy();
            if (isSteep()) {
                nextPos.y += sign;
                nextPos.x = getX(nextPos.y);
            } else {
                nextPos.x += sign;
                nextPos.y = getY(nextPos.x);
            }
            return currPos;
        }

    }

    /** if x>0, return 1, x=0, return 0, x<0, return -1. */
    private int getSign(int x) {
        return Integer.compare(x, 0);
    }

    /** Return the nearest int number. */
    private int rint(float x) {
        return (int) (x + 0.5f);
    }

    /**
     * Return ture if x is in the border of [x1, x2].
     * @param x1 border1 of the axis.
     * @param x2 border2 of the axis.
     * @param x the number that be checked whether in range.
     */
    private boolean inRange(int x1, int x2, int x) {
        return (x >= x1 && x <= x2) || (x <= x1 && x >= x2);
    }
}
