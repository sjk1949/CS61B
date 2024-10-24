package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
 *      - REMOVE -- file contains a list of files to be removed in the next commit
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
    /** The REMOVE file */
    public static final File REMOVE_FILE = join(GITLET_DIR, "REMOVE");

    /** SHA-1 id of the current commit. Use only after load(). Use save() to preserve */
    private static String HEAD;
    /** The current commit. Use only after load() */
    private static transient Commit HEAD_COMMIT;

    private static HashSet<String> REMOVE_LIST;

    /* TODO: fill in the rest of this class. */

    /**
     * init the repository in a folder without one. If already have one, print an error message.
     */
    public static void init() {
        if (GITLET_DIR.exists()) {
            Utils.message("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        // Init the repository
        GITLET_DIR.mkdir();
        COMMITS_DIR.mkdir();
        STAGE_DIR.mkdir();
        OBJECTS_DIR.mkdir();
        try{
            HEAD_FILE.createNewFile();
            REMOVE_FILE.createNewFile();
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
        // Create the first Commit
        Commit initCommit = new Commit("initial commit", new Date(0));
        HEAD = initCommit.saveCommit();
        REMOVE_LIST = new HashSet<>();
        save();
    }

    /**
     * This method is going to load the variables back. Also check if there is a .gitlet folder.
     */
    public static void load() {
        if (!GITLET_DIR.exists()) { // Check if in a gitlet repo
            Utils.message("Not in an initialized Gitlet directory.");
            // return; can't use in this place or the code will keep running
            System.exit(0);
        }
        HEAD = readContentsAsString(HEAD_FILE);
        HEAD_COMMIT = Commit.fromFile(HEAD);
        REMOVE_LIST = readObject(REMOVE_FILE, HashSet.class);
    }

    /**
     * This method is going to save the variables to files to set up persistence.
     */
    public static void save() {
        writeContents(HEAD_FILE, HEAD);
        writeObject(REMOVE_FILE, REMOVE_LIST);
    }

    /**
     * Save the file to the .gitlet/stage folder.
     * Check the current commit, if there is already an identical file, delete it.
     * If the file is not exist, exit.
     * If the file is added to the remove_list, remove it from the list.
     * @param filename the name of the file being added
     */
    public static void add(String filename) {
        load();
        File file = join(CWD, filename);
        if (!file.exists()) {
            message("File does not exist.");
            System.exit(0);
        }
        String filehash = sha1(filename, readContents(file)); // Use both the filename and contents to make sure they are identical
        REMOVE_LIST.remove(filename); // The case that the file is going to rm
        if (filehash.equals(HEAD_COMMIT.gethash(filename))) { // The situation that curr commit have similar file
            File stagedFile = join(STAGE_DIR, filename);
            if (stagedFile.exists()) {
                stagedFile.delete();
            }
        } else {
            File stageFile = join(STAGE_DIR, filename);
            byte[] content = readContents(file);
            writeContents(stageFile, (Object)content);
        }
        save();
    }

    /**
     * Create a commit which keep versions of files and update the staged file.
     * After that, HEAD pointer will point to it and remove all of the files in staging area and in remove_list.
     * @param message the commit message
     */
    public static void commit(String message) {
        load();
        Commit currCommit = Commit.fromFile(HEAD);
        if (message.isEmpty()) {
            message("Please enter a commit message.");
            System.exit(0);
        }
        Commit commit = new Commit(message, currCommit);
        // Add all of the files in the staging folder to the commit
        // If there is no files in the staging folder, exit
        List<String> files = plainFilenamesIn(STAGE_DIR);
        if (files.isEmpty() && REMOVE_LIST.isEmpty()) {
            message("No changes added to the commit.");
            System.exit(0);
        }
        // Remove the file in the remove_list. After done, clear the remove_list
        for (String removeFile : REMOVE_LIST) {
            commit.remove(removeFile);
        }
        REMOVE_LIST.clear();
        List<String> filesInObjects = plainFilenamesIn(OBJECTS_DIR);
        String filehash;
        for (String filename : files) {
            File file = join(STAGE_DIR, filename);
            filehash = commit.updateFile(file);
            // If this file is not exist, add it to the objects. We consider the SHA-1 id is unique.
            if (!filesInObjects.contains(filehash)) {
                File fileInObjects = join(OBJECTS_DIR, filehash);
                writeContents(fileInObjects, readContents(file));
            }
            file.delete(); // Remove all of the files in the staging area
        }
        HEAD = commit.saveCommit();
        save();
    }

    /**
     * Unstage the file if it's currently staged for addition
     * If the file is tracked in the current commit, stage it for removal and remove it from CWD(if user hasn't done)
     * If is neither staged nor tracked by head, exit
     */
    public static void rm(String filename) {
        load();
        List<String> files = plainFilenamesIn(STAGE_DIR);
        if (files.contains(filename)) {
            join(STAGE_DIR, filename).delete();
        } else if (HEAD_COMMIT.contains(filename)) {
            REMOVE_LIST.add(filename);
            if (join(CWD, filename).exists()) {
                restrictedDelete(filename);
            }
        } else {
            message("No reason to remove the file.");
            System.exit(0);
        }
        save();
    }

    /**
     * Print the commit tree until the initial commit.
     * TODO: merge commit
     */
    public static void log() {
        load();
        Commit commit;
        for (commit = HEAD_COMMIT; commit != null; commit = commit.getParent()) {
            System.out.printf(commit.toString());
        }
    }
}
