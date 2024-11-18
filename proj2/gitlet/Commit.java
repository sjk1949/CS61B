package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.MyUtils.exitWithError;
import static gitlet.MyUtils.getFilesAndFoldersFrom;
import static gitlet.Repository.*;
import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author sjk1949
 */
public class Commit implements Serializable, Comparable<Commit> {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private final String message;
    /** The time when the Commit is initialized. */
    private final Date timestamp;

    /** The SHA-1 id of the parent of this Commit. */
    private String parentHash;
    /** The parent of this Commit. Careful reload the parent when read Commit from file. */
    private transient Commit parent;
    /** The SHA-1 id of the second parent of this Commit when merge. */
    private String parentMergedHash;
    /** The second parent of this Commit when merge. Careful reload the parent when read Commit from file. */
    private transient Commit parentMerged;
    /** The map of hash code and filename of the files contain in this Commit.
     *  MUST be a TreeMap because hashMap will cause random result in serialize! */
    private TreeMap<String, String> fileMap;

    /* TODO: fill in the rest of this class. */
    /** A Commit object which use the local time as timestamp. */
    public Commit(String message) {
        this.message = message;
        this.timestamp = new Date();
        this.fileMap = new TreeMap<>();
    }

    /** A Commit object which use the given timestamp. */
    public Commit(String message, Date time) {
        this.message = message;
        this.timestamp = time;
        this.fileMap = new TreeMap<>();
    }

    /** A Commit object which set parent and copy its parent's fileList. */
    public Commit(String message, Commit parent) {
        this.message = message;
        this.timestamp = new Date();
        this.setParent(parent);
        this.copyFileMap(parent);
    }

    /** A Merge Commit object which set parent and second parent */
    public Commit(String message, Commit parent, Commit parentMerged) {
        this.message = message;
        this.timestamp = new Date();
        this.setParent(parent);
        this.setParentMerged(parentMerged);
        this.copyFileMap(parent);
    }

    /** Save the commit to file in .gitlet/commits/ for the sake of persistence.
     * @return the SHA-1 id of the Commit
     * */
    public String saveCommit() {
        String commitHash = this.getHash();
        // the first two digits of the hash is the commit's folder name.
        String head = commitHash.substring(0, 2);
        File commitFolder = join(COMMITS_DIR, head);
        if (!commitFolder.exists()) {
            commitFolder.mkdir();
        }
        File commitFile = join(commitFolder, commitHash);
        writeObject(commitFile, this);
        return commitHash;
    }

    /**
     * Reload a saved Commit from file. If input is null, return null. If file cannot find, return null.
     * @param filename the SHA-1 id of the Commit || the short id of the Commit
     *                 (Note that if there is collision, only the first commit will be provided.)
     */
    public static Commit fromFile(String filename) {
        if (filename == null) {
            return null;
        }
        String head = filename.substring(0, 2);
        File commitFolder = join(COMMITS_DIR, head);
        File commitFile = null;
        if (filename.length() == 40) {// if the given name is a normal hash id
            commitFile = join(commitFolder, filename);
        } else if (filename.length() < 40) {// if the given name is a short hash id
            commitFile = join(commitFolder, extendShortId(filename));
        }
        if (commitFile == null || !commitFile.exists()) {
            return null;
        }
        return readObject(commitFile, Commit.class);
    }

    /** extend the shortName to the first full commit name that exits, if failed, return origin shortName. */
    private static String extendShortId(String shortName) {
        String head = shortName.substring(0, 2);
        File commitFolder = join(COMMITS_DIR, head);
        if (!commitFolder.exists()) {
            return shortName;
        }
        List<String> commitsHash = plainFilenamesIn(commitFolder);
        for (String commitHash : commitsHash) {
            if (commitHash.startsWith(shortName)) {
                return commitHash;
            }
        }
        return shortName;
    }

    public void setParent(Commit parent) {
        this.parent = parent;
        this.parentHash = this.parent.getHash();
    }

    public void setParentMerged(Commit parent) {
        this.parentMerged = parent;
        this.parentMergedHash = this.parentMerged.getHash();
    }

    /** The safe way to get the parent commit */
    public Commit getParent() {
        if (this.parent == null) {
            this.parent = fromFile(this.parentHash);
        }
        return this.parent;
    }

    /** The safe way to get the second parent commit */
    public Commit getParentMerged() {
        if (this.parentMergedHash != null && this.parentMerged == null) {
            this.parentMerged = fromFile(this.parentMergedHash);
        }
        return this.parentMerged;
    }

    public void copyFileMap(Commit commit) {
        this.fileMap = new TreeMap<>();
        this.fileMap.putAll(commit.fileMap);
    }

    /**
     * Update the given file in the file map.
     * @param file the file that is going to save in the file map
     * @return the SHA-1 id of the file
     */
    public String updateFile(File file) {
        String filename = file.getName();
        String filehash = sha1(filename, readContents(file));
        this.fileMap.put(filename, filehash);
        return filehash;
    }

    /** Return the SHA-id of the given filename */
    public String getFileHash(String filename) {
        return this.fileMap.get(filename);
    }

    /** Return whether the file of this filename exists in this Commit */
    public boolean containsFile(String filename) {
        return this.fileMap.containsKey(filename);
    }

    public void removeFile(String filename) {
        this.fileMap.remove(filename);
    }

    public String getMessage() {
        return this.message;
    }

    /**
     * @return The SHA-1 id of this commit.
     */
    public String getHash() {
        return sha1(this.message, serialize(this.timestamp), serialize(this.fileMap));
    }

    /**
     * Return the file in the Object folder of the given filename.
     */
    public File getFile(String filename) {
        if (!this.containsFile(filename)) {
            return null;
        }
        return join(OBJECTS_DIR, this.getFileHash(filename));
    }

    /**
     * Return the filename set of the Commit
     */
    public Set<String> getFileNames() {
        return this.fileMap.keySet();
    }

    /**
     * Print the Commit in the following style:
     * ===
     * commit [commit SHA-1 id]
     * Date: [date]
     * [message]
     *
     */
    public String toString() {
        Formatter formatter = new Formatter(Locale.US);
        return "===\n" +
                "commit " + this.getHash() + "\n" +
                formatter.format("Date: %1$ta %1$tb %1$td %1$tT %1$tY %1$tz", this.timestamp) + "\n" +
                message + "\n" +
                "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Commit) {
            return ((Commit) obj).getHash().equals(this.getHash());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getHash());
    }

    @Override
    public int compareTo(Commit commit) {
        return this.hashCode() - commit.hashCode();
    }
}
