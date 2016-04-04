package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class LocalDeclarations extends StatementNode {

    VariableDeclaration head;

    public LocalDeclarations(int line, VariableDeclaration head) {
        this.line = line;
        this.head = head;
        this.kind = LOCAL_DECS;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Local Declarations\n");
        head.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
