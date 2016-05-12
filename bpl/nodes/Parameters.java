package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class Parameters extends DeclarationNode {

    public ParameterList pl;

    public Parameters(int line) {
        this.kind = PARAMS;
        this.line = line;
    }

    public Parameters(int line, ParameterList pl) {
        this(line);
        this.pl = pl;
    }

    public int markVariables(int position, int depth, FunctionDeclaration fd)
    {
        if (pl == null) return position;
        else return pl.markVariables(position, depth, fd);
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
