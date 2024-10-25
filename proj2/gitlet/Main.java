package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author sjk1949
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            myUtils.exitWithError("Please enter a command.");
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                validateNumArgs(args, 1);
                Repository.init();
                break;
            case "add":
                validateNumArgs(args, 2);
                Repository.add(args[1]);
                break;
            case "commit":
                validateNumArgs(args, 2);
                Repository.commit(args[1]);
                break;
            case "rm":
                validateNumArgs(args, 2);
                Repository.rm(args[1]);
                break;
            case "log":
                validateNumArgs(args, 1);
                Repository.log();
                break;
            case "global-log":
                validateNumArgs(args, 1);
                Repository.logGlobal();
                break;
            case "find":
                validateNumArgs(args, 2);
                Repository.find(args[1]);
                break;
            case "status":
                validateNumArgs(args, 1);
                Repository.status();
                break;
            case "checkout":
                if (args.length == 2) {//checkout [branch name]
                    Repository.checkoutBranch(args[1]);
                } else if (args.length == 3 && args[1].equals("--")) {//checkout -- [file name]
                    Repository.checkout(args[2]);
                } else if (args.length == 4 && args[2].equals("--")) {//checkout [commit id] -- [file name]
                    Repository.checkout(args[1], args[3]);
                } else {
                    myUtils.exitWithError("Incorrect operands.");
                }
                break;
            // TODO: FILL THE REST IN
            default:
                myUtils.exitWithError("No command with that name exists.");
        }
    }

    /**
     * check if the arguments number is n, if not, exit.
     * @param args the arguments
     * @param n the number expected
     */
    public static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            myUtils.exitWithError("Incorrect operands.");
        }
    }
}
