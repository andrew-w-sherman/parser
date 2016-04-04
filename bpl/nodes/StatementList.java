package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class StatementList extends StatementNode {

    Statement head;

    public StatementList(int line, Statement head) {
        this.line = line;
        this.head = head;
        this.kind = STATEMENT_LIST;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        head.findReferences(symbolTable, localDecs);
    }

    public void checkType(String rt) throws TypeException {
        head.checkType(rt);
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Statement List\n");
        head.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
