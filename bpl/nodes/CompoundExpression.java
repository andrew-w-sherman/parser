package bpl.nodes;
import bpl.TypeChecker;
import bpl.Token;
import bpl.LocalDecList;
import bpl.exceptions.*;
import java.util.HashMap;
public class CompoundExpression extends ExpressionNode {

    public ENode e1;
    public RelOp rel;
    public ENode e2;

    public CompoundExpression(int line, ENode e) {
        this.line = line;
        this.e1 = e;
        this.kind = COMP_EXP;
    }

    public CompoundExpression(int line, ENode e1, RelOp rel, ENode e2) {
        this(line, e1);
        this.rel = rel;
        this.e2 = e2;
    }

    public void findReferences(HashMap<String, DeclarationNode> symbolTable,
            LocalDecList localDecs) throws TypeException {
        e1.findReferences(symbolTable, localDecs);
        if (rel != null) {
            e2.findReferences(symbolTable, localDecs);
        }
    }

    public String checkType() throws TypeException {
        if (rel != null) {
            if (e1.checkType().equals("int") &&
                    e2.checkType().equals("int"))
                type = "int";
        }
        else
            type = e1.checkType();
        TypeChecker.debug("Compound Expression on " + line + " assigned " + 
                type);
        return type;
    }

    public boolean isLNode() {
        if (this.rel == null && e1.isLNode()) return true;
        return false;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("Compound Expression\n");
        e1.printRec(depth+1);
        if (rel != null) {
            rel.printRec(depth+1);
            e2.printRec(depth+1);
        }
        if (next != null) next.printRec(depth);
    }
}
