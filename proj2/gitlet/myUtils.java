package gitlet;

import static gitlet.Utils.*;

public class myUtils {

    /** Print a error message and exit */
    public static void exitWithError(String msg) {
        message(msg);
        System.exit(0);
    }
}
