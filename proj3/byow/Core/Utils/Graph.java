package byow.Core.Utils;

import java.util.*;

/** A graph data structure represented by an integer matrix, where -1 represents that there is no connection between two vertices. */
public class Graph<T> {

    private int edges;
    private final int[][] matrix;
    private final BiMap<Integer, T> map;

    public Graph(Collection<T> vertexes) {
        int V = vertexes.size();
        this.edges = 0;
        this.matrix = new int[V][V];
        this.map = new BiMap<>();
        int i = 0;
        for (T vertex : vertexes) {
            this.map.put(i, vertex);
            this.matrix[i][i] = -1;
            i++;
        }
    }

    public boolean existEdge(T v1, T v2) {
        return getWeight(v1, v2) != -1;
    }

    public int getWeight(T v1, T v2) {
        return matrix[map.getKey(v1)][map.getKey(v2)];
    }

    public Edge getEdge(T v1, T v2) {
        if (!existEdge(v1, v2)) {
            return null;
        }
        return new Edge(v1, v2, getWeight(v1, v2));
    }

    public void addEdge(T v1, T v2, int w) {
        if (!existEdge(v1, v2)) {
            this.edges += 1;
        }
        matrix[map.getKey(v1)][map.getKey(v2)] = w;
        matrix[map.getKey(v2)][map.getKey(v1)] = w;
    }

    public void removeEdge(T v1, T v2) {
        if (existEdge(v1, v2)) {
            this.edges -= 1;
        }
        matrix[map.getKey(v1)][map.getKey(v2)] = -1;
        matrix[map.getKey(v2)][map.getKey(v1)] = -1;
    }

    Set<T> adj(T v) {
        Set<T> adj = new HashSet<>();
        for (int i = 0; i < V(); i++) {
            if (matrix[map.getKey(v)][i] != -1) {
                T vertex = map.getValue(i);
                adj.add(vertex);
            }
        }
        return adj;
    }

    int V() {
        return matrix.length;
    }

    int E() {
        return edges;
    }

    public Set<T> getVertexSet() {
        return map.valueSet();
    }

    public Set<Edge> getEdgeSet() {
        Set<Edge> set = new HashSet<>();
        for (int i = 0; i < V(); i++) {
            for (int j = i + 1; j < V(); j++) {
                Edge edge = getEdge(map.getValue(i), map.getValue(j));
                if (edge != null) {
                    set.add(edge);
                }
            }
        }
        return set;
    }

    public class Edge implements Comparable<Edge> {

        public T v1;
        public T v2;
        public int weight;

        public Edge(T v1, T v2, int weight) {
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return weight - o.weight;
        }
    }
}
