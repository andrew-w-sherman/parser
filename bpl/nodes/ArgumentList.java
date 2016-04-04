package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class ArgumentList extends ExpressionNode {

    Expression head;

    public ArgumentList(int line, Expression head) {
        this.line = line;
        this.head = head;
        this.kind = ARG_LIST;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        head.findReferences(symbolTable, localDecs);
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Argument List\n");
        head.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
