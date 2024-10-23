package gitlet;

import java.io.File;
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

    /* TODO: fill in the rest of this class. */
}
