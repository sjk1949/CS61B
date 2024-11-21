package byow.Core;

import java.util.Iterator;

public class Line implements Iterable<Position> {

    public final Position startPos;
    public final Position endPos;

    public Line(Position startPos, Position endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    private float getY(int x) {
        assert startPos.x - endPos.x != 0;
        System.out.println("x:" + x + "y:" + ((float) (startPos.y - endPos.y) / (startPos.x - endPos.x) * (x - startPos.x) + startPos.y));
        return (float) (startPos.y - endPos.y) / (startPos.x - endPos.x) * (x - startPos.x) + startPos.y;
    }

    public boolean contains(Position pos) {
        if (startPos.x == endPos.x) {
            return inRange(startPos.y, endPos.y, pos.y);
        } else if (inRange(startPos.x, endPos.x, pos.x)) {
            return Math.abs(pos.y - getY(pos.x)) <= 0.5f;
        }
        return false;
    }

    @Override
    public Iterator<Position> iterator() {
        return new PosIterator();
    }

    private class PosIterator implements Iterator<Position> {

        private final Position currPos = startPos.copy();
        /** The direction x variate, if endPos.x > startPos.x, sign = 1, else, sign = -1, if ==, sign = 0. */
        private final int sign = getSign(endPos.x - startPos.x);

        @Override
        public boolean hasNext() {
            return !currPos.equals(endPos);
        }

        @Override
        public Position next() {
            Position returnPos = currPos.copy();
            currPos.x += sign;
            currPos.y = rint(getY(currPos.x));
            System.out.println(returnPos);
            return returnPos;
        }

        private int getSign(int x) {
            return Integer.compare(x, 0);
        }

        private int rint(float x) {
            return (int) (x + 0.5f);
        }
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
