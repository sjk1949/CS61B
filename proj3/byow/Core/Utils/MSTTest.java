package byow.Core.Utils;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class MSTTest {

    @Test
    public void test() {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            set.add(i);
        }
        Graph<Integer> graph = new Graph<>(set);
        graph.addEdge(1, 0, 1);
        graph.addEdge(1, 2, 50);
        graph.addEdge(1, 3, 1);
        graph.addEdge(1, 4, 1);
        graph.addEdge(2, 5, 10);
        graph.addEdge(2, 4, 23);
        graph.addEdge(3, 5, 1);
        graph.addEdge(3, 0, 100);
        graph.addEdge(0, 4, 4);
        graph.addEdge(4, 5, 22);
        System.out.println(graph);
        MST<Integer> mst = new MST<>(graph);
        Graph<Integer> graphMST = mst.findGraphMST();
        System.out.println(graphMST);
        System.out.println(graphMST.getEdgeSet());
        assertTrue(graphMST.existEdge(0, 1));
        assertTrue(graphMST.existEdge(1, 4));
        assertTrue(graphMST.existEdge(1, 3));
        assertTrue(graphMST.existEdge(3, 5));
        assertTrue(graphMST.existEdge(2, 5));
        assertFalse(graphMST.existEdge(0, 3));
        assertFalse(graphMST.existEdge(0, 2));
        assertFalse(graphMST.existEdge(4, 5));
    }
}
