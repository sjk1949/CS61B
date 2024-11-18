package gitlet;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int V;
    private List<Integer>[] adj;

    public Graph(int V) {
        this.V = V;
        adj = (List<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<Integer>();
        }
    }

    public void addEdge(int from, int to) {
        adj[from].add(to);
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
}
