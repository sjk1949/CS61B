package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> aListNoResizing = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        aListNoResizing.addLast(4);
        buggyAList.addLast(4);
        aListNoResizing.addLast(5);
        buggyAList.addLast(5);
        aListNoResizing.addLast(6);
        buggyAList.addLast(6);
        assertEquals(aListNoResizing.removeLast(), buggyAList.removeLast());
        assertEquals(aListNoResizing.removeLast(), buggyAList.removeLast());
        assertEquals(aListNoResizing.removeLast(), buggyAList.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> L2 = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                L2.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                assertEquals(size, L2.size());
            } else if (operationNumber == 2) {
                // getLast
                if (L.size() > 0) {
                    int last = L.getLast();
                    int last2 = L2.getLast();
                    assertEquals(last, last2);
                }
            } else if (operationNumber == 3) {
                // removeLast
                if (L.size() > 0) {
                    int last = L.removeLast();
                    int last2 = L2.removeLast();
                    assertEquals(last, last2);
                }
            }
        }
    }

}
