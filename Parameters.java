public class Parameters extends DeclarationNode {

    public ParameterList pl;

    public Parameters(int line) {
        this.type = PARAMS;
        this.line = line;
    }

    public Parameters(int line, ParameterList pl) {
        this(line);
        this.pl = pl;
    }

    public void printRec(int depth) {
        printDepth(depth);
        if (pl == null)
            System.out.print("No Parameters\n");
        else {
            System.out.print("Parameters:\n");
            pl.printRec(depth+1);
        }
        if (next != null) next.printRec(depth);
    }
}
