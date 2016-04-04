package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class IfStatement extends StatementNode {

    Expression ex;
    Statement st1;
    Statement st2;

    public IfStatement(int line, Expression ex, Statement st) {
        this.ex = ex;
        this.st1 = st;
        this.line = line;
        this.kind = IF_STMT;
    }

    public IfStatement(int line, Expression ex, Statement st1,
            Statement st2) {
        this(line, ex, st1);
        this.st2 = st2;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        ex.findReferences(symbolTable, localDecs);
        st1.findReferences(symbolTable, localDecs);
        if (st2 != null) st2.findReferences(symbolTable, localDecs);
    }

    public void checkType(String rt) throws TypeException {
        if (!ex.checkType().equals("int"))
            throw new TypeException(
                    "Predicate of if must be type int.", line);
        st1.checkType(rt);
        if (st2 != null) st2.checkType(rt);
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("If:\n");
        ex.printRec(depth+1);
        printDepth(depth);
        System.out.print("do:\n");
        st1.printRec(depth+1);
        if (st2 != null) {
            printDepth(depth);
            System.out.print("else:\n");
            st2.printRec(depth+1);
        }
        printDepth(depth);
        System.out.print("EndIf\n");
        if (next != null) next.printRec(depth);
    }
}
