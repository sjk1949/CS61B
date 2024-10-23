package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 * This class has the main logic of the application. It sets up and manges the persistence.
 * The structure of the repository is as follows:
 *
 * .gitlet/ top level folder of all persistent data
 *      - commits/ -- folder containing all commits that saved
 *      - stage/ -- folder containing all of the staging file through add command
 *      - objects/ -- folder containing each unique version of the file added through the commit command
 *      - HEAD -- file containing the current HEAD commit hash
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author sjk1949
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The .gitlet/commits directory. */
    public static final File COMMITS_DIR = join(GITLET_DIR, "commits");
    /** The .gitlet/stage directory. */
    public static final File STAGE_DIR = join(GITLET_DIR, "stage");
    /** The .gitlet/objects directory. */
    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");
    /** The HEAD file */
    public static final File HEAD_FILE = join(GITLET_DIR, "HEAD");

    /** SHA-1 id of the current commit. */
    private static String HEAD;

    /* TODO: fill in the rest of this class. */

    /**
     * init the repository in a folder without one. If already have one, print an error message.
     */
    public static void init() {
        if (GITLET_DIR.exists()) {
            Utils.message("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        // Init the repository
        GITLET_DIR.mkdir();
        COMMITS_DIR.mkdir();
        STAGE_DIR.mkdir();
        OBJECTS_DIR.mkdir();
        try{
            HEAD_FILE.createNewFile();
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
        // Create the first Commit
        Commit initCommit = new Commit("initial commit", new Date(0));
        HEAD = initCommit.saveCommit();
        writeContents(HEAD_FILE, HEAD);
    }

    public static void add(String filename) {}

    public static void commit() {}
}
