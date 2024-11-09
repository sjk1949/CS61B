package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static gitlet.Utils.*;
import static gitlet.Utils.plainFilenamesIn;
import static gitlet.MyUtils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 * This class has the main logic of the application. It sets up and manges the persistence.
 * The structure of the repository is as follows:
 *
 * .gitlet/ top level folder of all persistent data
 *      - commits/ -- folder containing all commits that saved
 *          - a0/ -- folder containing all commits with the first 2 digits
 *      - stage/ -- folder containing all of the staging file through add command
 *      - objects/ -- folder containing each unique version of the file added through the commit command
 *      - heads/ -- folder containing the heads of branches
 *      - HEAD -- file containing the current branch name or HEAD commit hash
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
    /** The .gitlet/heads directory. */
    public static final File HEADS_DIR = join(GITLET_DIR, "heads");
    /** The HEAD file */
    public static final File HEAD_FILE = join(GITLET_DIR, "HEAD");
    /** The REMOVE file */
    public static final File REMOVE_FILE = join(GITLET_DIR, "REMOVE");

    /** Normally, HEAD will be a dir to a branch head, If it's a detached HEAD, it'll contain SHA-1 id of the current commit. Use save() to preserve */
    private static String HEAD;
    /** The current commit. */
    private static String HEAD_COMMIT_HASH;
    private static transient Commit HEAD_COMMIT;

    /** The name of the current branch */
    private static String BRANCH;

    private static TreeSet<String> REMOVE_LIST;

    /* TODO: fill in the rest of this class. */

    /**
     * init the repository in a folder without one.
     */
    public static void init() {
        // Init the repository
        GITLET_DIR.mkdir();
        COMMITS_DIR.mkdir();
        STAGE_DIR.mkdir();
        OBJECTS_DIR.mkdir();
        HEADS_DIR.mkdir();
        try{
            HEAD_FILE.createNewFile();
            REMOVE_FILE.createNewFile();
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
        // Create the first Commit
        Commit initCommit = new Commit("initial commit", new Date(0));
        updateBranch("master", initCommit.saveCommit());
        setHEADtoBranch("master");
        REMOVE_LIST = new TreeSet<>();
        save();
    }

    /** Check whether the repo is exist. */
    public static boolean isRepo() {
        return GITLET_DIR.exists();
    }

    /**
     * This method is going to save the variables to files to set up persistence.
     */
    public static void save() {
        writeContents(HEAD_FILE, getHEAD());
        writeObject(REMOVE_FILE, getRemoveList());
    }

    public static void printStatus() {
        List<String> branchs = plainFilenamesIn(HEADS_DIR);
        List<String> stagedFiles = plainFilenamesIn(STAGE_DIR);
        System.out.println("=== Branches ===");
        for (String branch : branchs) {
            if (branch.equals(getBRANCH())) {
                System.out.print('*');
            }
            System.out.println(branch);
        }
        System.out.println("\n=== Staged Files ===");
        for (String stagedFile : stagedFiles) {
            System.out.println(stagedFile);
        }
        System.out.println("\n=== Removed Files ===");
        for (String removedFile : getRemoveList()) {
            System.out.println(removedFile);
        }
        System.out.println("\n=== Modifications Not Staged For Commit ===");
        System.out.println("\n=== Untracked Files ===");
        System.out.println();
    }

    /** Set the HEAD point to the given branch */
    public static void setHEADtoBranch(String branchname) {
        HEAD = "branch:" + branchname;
        HEAD_COMMIT = getBranchHeadCommit(branchname);
    }

    /** Set the HEAD point to the given commit(detached)*/
    public static void setHEADtoCommit(String commithash) {
        HEAD = commithash;
        HEAD_COMMIT = Commit.fromFile(commithash);
    }

    /** Get the current branch if there is one, if not, return null */
    public static String getBRANCH() {
        if (BRANCH == null) {
            String headContent = getHEAD();
            if (headContent.startsWith("branch:")) {
                BRANCH = headContent.substring("branch:".length());
            }
        }
        return BRANCH;
    }

    /** Update the branch using the given commit SHA-1 id */
    public static void updateBranch(String branchname, String commithash) {
        File branchFile = getBranchFile(branchname);
        writeContents(branchFile, commithash);
    }


    /** get the Commit SHA-1 id inside the branch file */
    public static String getBranchHead(String branchname) {
        File branchFile = getBranchFile(branchname);
        if (!branchFile.exists()) {
            exitWithError("No such branch exists.");
        }
        return readContentsAsString(branchFile);
    }

    /** get the head Commit of the given branch, if not found, exit with error */
    public static Commit getBranchHeadCommit(String branchname) {
        return Commit.fromFile(getBranchHead(branchname));
    }

    /** get branch file */
    private static File getBranchFile(String branchname) {
        return join(HEADS_DIR, branchname);
    }

    /** delete the branch with given name */
    public static void removeBranch(String branchname) {
        getBranchFile(branchname).delete();
    }

    /** get branch names */
    public static List<String> getBranchNames() {
        return plainFilenamesIn(HEADS_DIR);
    }

    /** clear the staging area */
    public static void clearStagingArea() {
        clear(STAGE_DIR);
        getRemoveList().clear();
    }

    /** clear the working directory */
    public static void clearCWD() {
        clear(CWD);
    }

    /** clear the given directory */
    private static void clear(File dir) {
        Set<File> files = getFilesFrom(dir);
        for (File file : files) {
            file.delete(); // Remove all of the files in the dir
        }
    }

    /** get HEAD_COMMIT SHA-1 id */
    public static String getHEAD() {
        if (HEAD == null) {
            HEAD = readContentsAsString(HEAD_FILE);
        }
        return HEAD;
    }

    /** get HEAD_COMMIT hash */
    public static  String getHeadCommitHash() {
        if (HEAD_COMMIT_HASH == null) {
            String headContent = getHEAD();
            if (headContent.startsWith("branch:")) {
                HEAD_COMMIT_HASH = getBranchHead(getBRANCH());
            } else {
                HEAD_COMMIT_HASH = getHEAD();
            }
        }
        return HEAD_COMMIT_HASH;
    }

    /** get HEAD_COMMIT */
    public static Commit getHeadCommit() {
        if (HEAD_COMMIT == null) {
            HEAD_COMMIT = Commit.fromFile(getHeadCommitHash());
        }
        return HEAD_COMMIT;
    }

    /** get all of the commits */
    public static Set<Commit> getCommits() {
        Set<Commit> commits = new TreeSet<>();
        Set<File> commitsFolders = getFilesAndFoldersFrom(COMMITS_DIR);
        for (File commitsFolder : commitsFolders) {
            List<String> commitsHash = plainFilenamesIn(commitsFolder);
            for (String commithash : commitsHash) {
                commits.add(Commit.fromFile(commithash));
            }
        }
        return commits;
    }

    /** get REMOVE_LIST */
    public static TreeSet<String> getRemoveList() {
        if (REMOVE_LIST == null) {
            REMOVE_LIST = readObject(REMOVE_FILE, TreeSet.class);
        }
        return REMOVE_LIST;
    }
}
