package gitlet;

import java.io.File;

import static gitlet.Utils.*;

public class Test {

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));

    public static void main(String[] args) {
        File file1 = join(CWD, "a");
        File file2 = join(CWD, "a");
        System.out.println(file1.equals(file2));
    }
}
