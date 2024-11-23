package byow.Core.Utils;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DisjointSetTest {

    @Test
    public void test() {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
        DisjointSet<Integer> disjointSet = new DisjointSet<>(set);
        disjointSet.union(0, 1);
        disjointSet.union(2, 30);
        assertFalse(disjointSet.isUnion(1, 2));
        assertTrue(disjointSet.isUnion(0, 1));
        disjointSet.union(1, 2);
        assertTrue(disjointSet.isUnion(0, 30));
    }
}
