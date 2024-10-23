package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class

import static gitlet.Repository.*;
import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author sjk1949
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    /** The time when the Commit is initialized. */
    private Date timestamp;

    /** The parent of this Commit. */
    private Commit parent;
    /** The list of hash code of the files contain in this Commit. */
    private String[] hashList;

    /* TODO: fill in the rest of this class. */
    /** A Commit object which use the local time as timestamp. */
    public Commit(String message) {
        this.message = message;
        this.timestamp = new Date();
    }

    /** A Commit object which use the given timestamp. */
    public Commit(String message, Date time) {
        this.message = message;
        this.timestamp = time;
    }

    /** Save the commit to file in .gitlet/commits/ for the sake of persistence.
     * @return the SHA-1 id of the Commit
     * */
    public String saveCommit() {
        String commitHash = sha1(serialize(this));
        File commitFile = join(COMMITS_DIR, commitHash);
        writeObject(commitFile, this);
        return commitHash;
    }

    /**
     * Reload a saved Commit from file.
     * @param filename the SHA-1 id of the Commit
     */
    public Commit fromFile(String filename) {
        File commitFile = join(COMMITS_DIR, filename);
        return readObject(commitFile, Commit.class);
    }
}
