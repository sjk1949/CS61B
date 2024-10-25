package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static gitlet.Utils.*;
import static gitlet.Utils.plainFilenamesIn;
import static gitlet.myUtils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 * This class has the main logic of the application. It sets up and manges the persistence.
 * The structure of the repository is as follows:
 *
 * .gitlet/ top level folder of all persistent data
 *      - commits/ -- folder containing all commits that saved
 *      - stage/ -- folder containing all of the staging file through add command
 *      - objects/ -- folder containing each unique version of the file added through the commit command
 *      - heads/ -- folder containing the heads of branches
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
     * init the repository in a folder without one. If already have one, print an error message.
     */
    public static void init() {
        if (GITLET_DIR.exists()) {
            exitWithError("A Gitlet version-control system already exists in the current directory.");
        }
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
        setHEADusingBranch("master");
        REMOVE_LIST = new TreeSet<>();
        save();
    }

    /**
     * This method is going to load the variables back. Also check if there is a .gitlet folder.
     */
    public static void load() {
        if (!GITLET_DIR.exists()) { // Check if in a gitlet repo
            exitWithError("Not in an initialized Gitlet directory.");
            // return; can't use in this place or the code will keep running
        }
        // TODO:
    }

    /**
     * This method is going to save the variables to files to set up persistence.
     */
    public static void save() {
        writeContents(HEAD_FILE, getHEAD());
        writeObject(REMOVE_FILE, getRemoveList());
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
            exitWithError("File does not exist.");
        }
        String filehash = sha1(filename, readContents(file)); // Use both the filename and contents to make sure they are identical
        getRemoveList().remove(filename); // The case that the file is going to rm
        if (filehash.equals(getHeadCommit().getFileHash(filename))) { // The situation that curr commit have similar file
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
        Commit currCommit = getHeadCommit();
        if (message.isEmpty()) {
            exitWithError("Please enter a commit message.");
        }
        Commit commit = new Commit(message, currCommit);
        // Add all of the files in the staging folder to the commit
        // If there is no files in the staging folder, exit
        Set<File> files = getFilesFrom(STAGE_DIR);
        if (files.isEmpty() && getRemoveList().isEmpty()) {
            exitWithError("No changes added to the commit.");
        }
        // Remove the file in the remove_list. After done, clear the remove_list
        for (String removeFile : getRemoveList()) {
            commit.removeFile(removeFile);
        }
        List<String> filesInObjects = plainFilenamesIn(OBJECTS_DIR);
        String filehash;
        for (File file : files) {
            filehash = commit.updateFile(file);
            // If this file is not exist, add it to the objects. We consider the SHA-1 id is unique.
            if (!filesInObjects.contains(filehash)) {
                File fileInObjects = join(OBJECTS_DIR, filehash);
                writeContents(fileInObjects, readContents(file));
            }
        }
        clearStagingArea();
        updateBranch(getBRANCH(), commit.saveCommit());
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
        } else if (getHeadCommit().containsFile(filename)) {
            getRemoveList().add(filename);
            if (join(CWD, filename).exists()) {
                restrictedDelete(filename);
            }
        } else {
            exitWithError("No reason to remove the file.");
        }
        save();
    }

    /**
     * Print the commit tree until the initial commit. Begin at HEAD_COMMIT
     * TODO: merge commit
     */
    public static void log() {
        load();
        Commit commit;
        for (commit = getHeadCommit(); commit != null; commit = commit.getParent()) {
            System.out.printf(commit.toString());
        }
    }

    /**
     * Print all of the commits ever made ignoring their order.
     */
    public static void logGlobal() {
        load();
        List<String> commitsHash = plainFilenamesIn(COMMITS_DIR);
        for (String commithash : commitsHash) {
            Commit commit = Commit.fromFile(commithash);
            System.out.printf(commit.toString());
        }
    }

    /** Find the commits with the given message */
    public static void find(String message) {
        load();
        boolean hasCommit = false;
        List<String> commitsHash = plainFilenamesIn(COMMITS_DIR);
        for (String commithash : commitsHash) {
            Commit commit = Commit.fromFile(commithash);
            if (commit.getMessage().equals(message)) {
                hasCommit = true;
                System.out.println(commit.getHash());
            }
        }
        if (!hasCommit) {
            exitWithError("Found no commit with that message.");
        }
    }

    /** Display the branches, staged files, removed files , modifications not staged and untracked files. */
    public static void status() {
        printStatus();
    }

    /**
     * Replace the file using the file from the head commit with the same name
     * @param filename the file that is going to repalced
     */
    public static void checkout(String filename) {
        load();
        checkout(getHeadCommit(), filename);
    }

    /** Replace the file using the file from the given commit with the same name */
    public static void checkout(String commithash, String filename) {
        load();
        Commit commit = Commit.fromFile(commithash);
        if (commit == null) {
            exitWithError("No commit with that id exists.");
        }
        checkout(commit, filename);
    }

    /** Change current branch to the given branch */
    public static void checkoutBranch(String branchname) {
        load();
        Commit commit = getBranchHeadCommit(branchname);
        Commit currCommit = getHeadCommit();
        if (commit.equals(currCommit)) {
            exitWithError("No need to checkout the current branch.");
        }
        Set<String> currFilenames = currCommit.getFileNames();
        // Check if there is a untracked file before changes the working dir
        List<String> workingFiles = plainFilenamesIn(CWD);
        for (String workingFile : workingFiles) {
            if (!currCommit.containsFile(workingFile)) {
                exitWithError("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }
        clearCWD();// Delete all of the files tracked in the currCommit
        clearStagingArea();
        Set<String> filenames = commit.getFileNames();
        for (String filename : filenames) {
            checkout(commit, filename);
        }
        setHEADusingBranch(branchname);
        save();
    }

    /** Replace the file using the file from the given commit with the same name */
    private static void checkout(Commit commit, String filename) {
        if (!commit.containsFile(filename)) {
            exitWithError("File does not exist in that commit.");
        }
        File currFile = join(CWD, filename);
        File newFile = commit.getFile(filename);
        writeContents(currFile, readContents(newFile)); // Overwrite the old file
    }

    private static void printStatus() {
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

    /** Return a list of files in the given directory. */
    private static Set<File> getFilesFrom(File dir) {
        Set<File> fileList = new TreeSet<>();
        List<String> filenames = plainFilenamesIn(dir);
        for (String filename : filenames) {
            fileList.add(join(dir, filename));
        }
        return fileList;
    }

    /** Set the branch to the given name */
    private static void setHEADusingBranch(String branchname) {
        HEAD = "branch:" + branchname;
    }

    /** Get the current branch if there is one, if not, return null */
    private static String getBRANCH() {
        if (BRANCH == null) {
            String headContent = getHEAD();
            if (headContent.startsWith("branch:")) {
                BRANCH = headContent.substring("branch:".length());
            }
        }
        return BRANCH;
    }

    /** Update the branch using the given commit SHA-1 id */
    private static void updateBranch(String branchname, String commithash) {
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

    /** clear the staging area */
    private static void clearStagingArea() {
        clear(STAGE_DIR);
        getRemoveList().clear();
    }

    /** clear the working directory */
    private static void clearCWD() {
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

    /** get REMOVE_LIST */
    public static TreeSet<String> getRemoveList() {
        if (REMOVE_LIST == null) {
            REMOVE_LIST = readObject(REMOVE_FILE, TreeSet.class);
        }
        return REMOVE_LIST;
    }
}
