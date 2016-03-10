package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class LocalDeclarations extends StatementNode {

    VariableDeclaration head;

    public LocalDeclarations(int line, VariableDeclaration head) {
        this.line = line;
        this.head = head;
        this.type = LOCAL_DECS;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Local Declarations\n");
        head.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
