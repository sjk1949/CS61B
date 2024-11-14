package gitlet;

import java.util.HashSet;
import java.util.Set;

public class SplitPointFinder {

    /**
     * Find the latest co-ancestor of both commits
     * Note that if the commit is in the same branch, the ancestor may be one commit itself.
     * @return the Split Point Commit of Commit1 & Commit2
     */
    public static Commit findSplitPoint(Commit commit1, Commit commit2) {
        Commit commit;
        Set<Commit> visitedCommit = new HashSet<>();
        for (commit = commit1; commit != null; commit = commit.getParent()) {
            visitedCommit.add(commit);
        }
        for (commit = commit2; commit != null; commit = commit.getParent()) {
            if (visitedCommit.contains(commit)) {
                return commit;
            }
        }
        return null;
    }

}
