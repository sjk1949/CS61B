package byow.Core.Utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MST<T> {

    private final Graph<T> graph;
    private Graph.Edge[] edges;
    private Set<Graph<T>.Edge> mstEdges;

    public MST(Graph<T> graph) {
        this.graph = graph;
        this.edges = this.graph.getEdgeSet().toArray(new Graph.Edge[0]);
        Arrays.sort(this.edges);
        this.mstEdges = new HashSet<>();
    }

    public Graph<T> findMST() {
        Graph<T> graph = new Graph<>(this.graph.getVertexSet());
        for (Graph.Edge edge : edges) {

        }
        return graph;
    }
}
