package gitlet;

import java.io.File;
import java.util.List;
import java.util.Set;

import static gitlet.Repository.*;
import static gitlet.Utils.*;
import static gitlet.MyUtils.*;

public class Command {

    /**
     * This method is going to load the variables back. Also check if there is a .gitlet folder.
     */
    public static void load() {
        if (!Repository.isRepo()) { // Check if in a gitlet repo
            exitWithError("Not in an initialized Gitlet directory.");
            // return; can't use in this place or the code will keep running
        }
        // TODO:
    }

    /**
     * This method is going to save the variables to files to set up persistence.
     */
    public static void save() {
        Repository.save();
    }

    /**
     * init the repository in a folder without one. If already have one, print an error message.
     */
    public static void init() {
        if (Repository.isRepo()) {
            exitWithError("A Gitlet version-control system already exists in the current directory.");
        }
        Repository.init();
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
        Set<File> commitsFolders = getFilesAndFoldersFrom(COMMITS_DIR);
        for (File commitsFolder : commitsFolders) {
            List<String> commitsHash = plainFilenamesIn(commitsFolder);
            for (String commithash : commitsHash) {
                Commit commit = Commit.fromFile(commithash);
                System.out.printf(commit.toString());
            }
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
        if (getBRANCH().equals(branchname)) {
            exitWithError("No need to checkout the current branch.");
        }
        checkout(commit);
        setHEADtoBranch(branchname);
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

    /** Replace the working directory using the files from the given commit with the same name */
    private static void checkout(Commit commit) {
        Commit currCommit = getHeadCommit();
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
    }

    /**
     * create a branch with the given name, if the name is used, exit with error.
     * @param branchname The name of the new branch
     */
    public static void branch(String branchname) {
        if (getBranchNames().contains(branchname)) {
            exitWithError("A branch with that name already exists.");
        }
        // Point the branch at the current head commit
        updateBranch(branchname, getHeadCommitHash());
    }

    /** Delete the branch with the given name. */
    public static void rmBranch(String branchname) {
        if (!getBranchNames().contains(branchname)) {
            exitWithError("A branch with that name does not exist.");
        }
        if (getBRANCH().equals(branchname)) {
            exitWithError("Cannot remove the current branch.");
        }
        removeBranch(branchname);
    }

    /** Check out all files by the given commit, remove tracked files that are not present in the commit. */
    public static void reset(String commithash) {
        load();
        Commit commit = Commit.fromFile(commithash);
        if (commit == null) {
            exitWithError("No commit with that id exists.");
        }
        checkout(commit);
        updateBranch(getBRANCH(), commithash);
    }

    /** Merge files from the given branch to the current branch. */
    public static void merge(String branchName) {
        //TODO:
    }
}
