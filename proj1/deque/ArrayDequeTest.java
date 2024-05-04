package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void getTest() {
        ArrayDeque<Integer> L;
        // [1, 2, 3, ..., null]
        L = new ArrayDeque<>();
        L.addLast(1);
        L.addLast(2);
        L.addLast(3);
        assertEquals(1, (int) L.get(0));
        assertEquals(2, (int) L.get(1));
        assertEquals(3, (int) L.get(2));
        // [null, null, 4, 5, 6, ..., null]
        L = new ArrayDeque<>();
        L.addFirst(-1);
        L.addLast(1);
        L.addLast(2);
        L.addLast(4);
        L.addLast(5);
        L.addLast(6);
        L.removeFirst();
        L.removeFirst();
        L.removeFirst();
        assertEquals(4, (int) L.get(0));
        assertEquals(5, (int) L.get(1));
        assertEquals(6, (int) L.get(2));
    }

    /** This Test is forbidden because it's usage of private valuable and method, however, resize() is
     * such a difficult job that isn't usually happen when used Deque normally, I don't know whether there
     * is a better choice rather than change it to public and test it.

    @Test
    public void resizeTest() {
        ArrayDeque<Integer> L;
        Integer[] expect;
        int capacity = 16;
        // [1, 2, 3, ..., null]
        expect = new Integer[capacity];
        expect[0] = 1;expect[1] = 2;expect[2] = 3;
        L = new ArrayDeque<>();
        L.addLast(1);L.addLast(2);L.addLast(3);
        L.resize(16);
        assertEquals(expect, L.items);
        // [4, 5, 6, null, ..., 1, 2]
        expect = new Integer[capacity];
        expect[0] = 1;expect[1] = 2;expect[2] = 4;expect[3] = 5;expect[4] = 6;
        L = new ArrayDeque<>();
        L.addFirst(2);L.addFirst(1);
        L.addLast(4);L.addLast(5);L.addLast(6);
        L.resize(capacity);
        assertEquals(expect, L.items);
        // [6, 7, 8,| 1, 2, 3, 4, 5]
        expect = new Integer[capacity];
        expect[0] = 1;expect[1] = 2;expect[2] = 3;expect[3] = 4;expect[4] = 5;expect[5] = 6;expect[6] = 7;expect[7] = 8;
        L = new ArrayDeque<>();
        L.addFirst(5);L.addFirst(4);L.addFirst(3);L.addFirst(2);L.addFirst(1);
        L.addLast(6);L.addLast(7);L.addLast(8);
        L.resize(capacity);
        assertEquals(expect, L.items);
        // [1, 2, 3, ..., 7, 8] (32)
        expect = new Integer[capacity];
        expect[0] = 7;expect[1] = 8;expect[2] = 1;expect[3] = 2;expect[4] = 3;
        L = new ArrayDeque<>();
        L.resize(32);
        L.addFirst(8);L.addFirst(7);
        L.addLast(1);L.addLast(2);L.addLast(3);
        L.resize(capacity);
        assertEquals(expect, L.items);
    }
    */
}
