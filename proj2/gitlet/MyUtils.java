package gitlet;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static gitlet.Utils.*;

public class myUtils {

    /** Print a error message and exit */
    public static void exitWithError(String msg) {
        message(msg);
        System.exit(0);
    }

    /** Return a list of files in the given directory. */
    public static Set<File> getFilesFrom(File dir) {
        Set<File> fileList = new TreeSet<>();
        List<String> filenames = plainFilenamesIn(dir);
        for (String filename : filenames) {
            fileList.add(join(dir, filename));
        }
        return fileList;
    }
}
