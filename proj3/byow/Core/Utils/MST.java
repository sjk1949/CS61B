package byow.Core.Utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MST<T> {

    private final Graph<T> graph;
    private final Graph<T>.Edge[] edges;
    private Graph<T> graphMST;

    public MST(Graph<T> graph) {
        this.graph = graph;
        this.edges = this.graph.getEdgeSet().toArray(new Graph.Edge[0]);
        Arrays.sort(this.edges);
    }

    public Graph<T> findGraphMST() {
        if (this.graphMST != null) {
            return this.graphMST;
        }
        Graph<T> graphMST = new Graph<>(graph.getVertexSet());
        DisjointSet<T> disjointSet = new DisjointSet<>(this.graph.getVertexSet());
        for (Graph<T>.Edge edge : edges) {
            if (!disjointSet.isUnion(edge.v1, edge.v2)) {
                disjointSet.union(edge.v1, edge.v2);
                graphMST.addEdge(edge);
            }
        }
        this.graphMST = graphMST;
        return graphMST;
    }
}
