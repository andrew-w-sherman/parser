package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class Statement extends StatementNode {

    public StatementNode sn;
    public Statement next;

    public Statement(int line, StatementNode child) {
        this.line = line;
        this.kind = STATEMENT;
        sn = child;
    } 

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        sn.findReferences(symbolTable, localDecs);
        if (next != null) next.findReferences(symbolTable, localDecs);
    }

    public void checkType(String rt) throws TypeException {
        sn.checkType(rt);
        if (next != null) next.checkType(rt);
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Statement\n");
        sn.printRec(depth + 1);
        if(next != null) next.printRec(depth);
    }
}
