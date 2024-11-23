package byow.Core.Utils;

import java.util.Arrays;
import java.util.Set;

public class DisjointSet<T> {

    private final int[] tree;
    private final BiMap<Integer, T> map;

    public DisjointSet(Set<T> set) {
        this.tree = new int[set.size()];
        Arrays.fill(this.tree, -1);
        this.map = BiMap.createIndexMap(set);
    }

    public void union(T a, T b) {
        union(map.getKey(a), map.getKey(b));
    }

    private void union(int a, int b) {
        int aRoot = root(a);
        int bRoot = root(b);
        if (aRoot != bRoot) {
            if (tree[aRoot] < tree[bRoot]) {
                tree[aRoot] += tree[bRoot];
                tree[bRoot] = aRoot;
            } else {
                tree[bRoot] += tree[aRoot];
                tree[aRoot] = bRoot;
            }
        }
    }

    public boolean isUnion(T a, T b) {
        return isUnion(map.getKey(a), map.getKey(b));
    }

    private boolean isUnion(int a, int b) {
        return root(a) == root(b);
    }

    private int root(int a) {
        if (tree[a] < 0) {
            return a;
        } else {
            return tree[a] = root(tree[a]);
        }
    }
}
