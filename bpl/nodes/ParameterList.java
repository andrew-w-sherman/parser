package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class ParameterList extends DeclarationNode {

    public Parameter head;

    public ParameterList(int line, Parameter head) {
        this.line = line;
        this.head = head;
        this.kind = PARAM_LIST;
    }

    public int markVariables(int position, int depth, FunctionDeclaration fd)
    {
        return head.markVariables(position, depth, fd);
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Parameter List\n");
        head.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
