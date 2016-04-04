package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class ReturnStatement extends StatementNode {

    Expression ex;

    public ReturnStatement(int line) {
        this.line = line;
        this.kind = RETURN_STMT;
    }

    public ReturnStatement(int line, Expression ex) {
        this(line);
        this.ex = ex;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        ex.findReferences(symbolTable, localDecs);
    }

    public void checkType(String rt) throws TypeException {
        if (!ex.checkType().equals(rt))
            throw new TypeException("Improper return type.", line);
        TypeChecker.debug("Good return at " + line + ", type is " + rt);
    }

    public void printRec(int depth) {
        printDepth(depth);
        if (ex != null) {
            System.out.print("Return:\n");
            ex.printRec(depth+1);
        }
        else
            System.out.print("Return void\n");
        if (next != null) next.printRec(depth);
    }
}
