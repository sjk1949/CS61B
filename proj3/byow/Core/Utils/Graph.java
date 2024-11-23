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
        initialize(this.matrix);
        this.map = BiMap.createIndexMap(vertexes);
    }

    private void initialize(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = -1;
            }
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

    public void addEdge(Edge edge) {
        addEdge(edge.v1, edge.v2, edge.weight);
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

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("Graph\n");
        string.append("\t");
        for (T v : getVertexSet()) {
            string.append(v.toString()).append("\t");
        }
        string.append("\n");
        for (T v1 : getVertexSet()) {
            string.append(v1.toString()).append("\t");
            for (T v2 : getVertexSet()) {
                string.append(getWeight(v1, v2)).append("\t");
            }
            string.append("\n");
        }
        return string.toString();
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

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (obj.getClass() == Edge.class) {
                Edge edge = (Edge) obj;
                if ((this.v1.equals(edge.v1) && this.v2.equals(edge.v2)) || (this.v1.equals(edge.v2) && this.v2.equals(edge.v1))) {
                    return this.weight == edge.weight;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(v1.hashCode() + v2.hashCode(), weight);
        }

        @Override
        public String toString() {
            return "(" + v1.toString() + ", " + v2.toString() + ")";
        }
    }
}
