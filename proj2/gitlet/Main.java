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
            Utils.message("Please enter a command.");
            System.exit(0);
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                validateNumArgs(args, 1);
                // TODO: handle the `init` command
                break;
            case "add":
                validateNumArgs(args, 2);
                // TODO: handle the `add [filename]` command
                break;
            // TODO: FILL THE REST IN
            case "commit":
                validateNumArgs(args, 2);
                // TODO: handle the `commit [message]` command
                break;
            default:
                Utils.message("No command with that name exists.");
                System.exit(0);
        }
    }

    /**
     * check if the arguments number is n, if not, exit.
     * @param args the arguments
     * @param n the number expected
     */
    public static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            Utils.message("Incorrect operands.");
            System.exit(0);
        }
    }
}
