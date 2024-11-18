package gitlet;

import java.util.*;

import static gitlet.Repository.COMMITS_DIR;
import static gitlet.Repository.getCommits;
import static gitlet.Utils.plainFilenamesIn;

public class SplitPointFinder {

    private final Graph graph;
    private final int[] edgeTo;
    private final int[] distTo;
    private final int[] marked;

    private Queue queue;
    private Map<Integer, Commit> intToCommit;

    public SplitPointFinder() {
        this.intToCommit = new TreeMap<>();
        Set<Commit> commits = getCommits();
        int V = commits.size();
        int i = 0;
        for (Commit commit : commits) {
            intToCommit.put(i, commit);
            i += 1;
        }
        this.graph = new Graph(V);
        this.edgeTo = new int[V];
        this.distTo = new int[V];
        for (i = 0; i < V; i++) {
            this.edgeTo[i] = -1;
            this.distTo[i] = -1;
        }
        this.marked = new int[V];
        this.queue = new LinkedList<Integer>();
        for(i = 0; i < V; i++) {
            Commit commit = intToCommit.get(i);
            Commit parent = commit.getParent();
            Commit parentMerged = commit.getParentMerged();
            if (parent != null) {
                this.graph.addEdge(i, commitToInt(parent));
            }
            if (parentMerged != null) {
                this.graph.addEdge(i, commitToInt(parentMerged));
            }
        }
    }

    /**
     * Find the latest co-ancestor of both commits
     * Note that if the commit is in the same branch, the ancestor may be one commit itself.
     * @return the Split Point Commit of Commit1 & Commit2
     */
    public Commit findSplitPoint(Commit currCommit, Commit givenCommit) {
        int v = commitToInt(currCommit);
        this.queue.offer(v);
        this.marked[v] = 1;
        this.distTo[v] = 0;
        this.edgeTo[v] = v;
        while (!this.queue.isEmpty()) {
            v = (Integer) this.queue.poll();
            List<Integer> adj = (List<Integer>) this.graph.adj(v);
            for (int w: adj) {
                if (this.marked[w] == 0) {
                    this.marked[w] = 1;
                    this.distTo[w] = this.distTo[v] + 1;
                    this.edgeTo[w] = v;
                    this.queue.offer(w);
                }
            }
        }

        Integer closestPoint = findClosest(findConnection(commitToInt(givenCommit)));
        return intToCommit.get(closestPoint);
    }

    private Integer commitToInt(Commit commit) {
        for (Map.Entry<Integer, Commit> entry : intToCommit.entrySet()) {
            if (entry.getValue().equals(commit)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private Set<Integer> findConnection(int num) {
        Set<Integer> connectionNum = new TreeSet<>();
        if (this.distTo[num] != -1) {
            if (!connectionNum.contains(num)) {
                connectionNum.add(num);
                return connectionNum;
            }
        }
        List<Integer> adj = (List<Integer>) this.graph.adj(num);
        for (int i : adj) {
            connectionNum.addAll(findConnection(i));
        }
        return connectionNum;
    }

    private Integer findClosest(Collection<Integer> V) {
        Integer closest = null;
        int minDist = Integer.MAX_VALUE;
        for (int i : V) {
            if (distTo[i] < minDist) {
                minDist = distTo[i];
                closest = i;
            }
        }
        return closest;
    }

}
