package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class CompoundExpression extends ExpressionNode {

    public ENode e1;
    public RelOp rel;
    public ENode e2;

    public CompoundExpression(int line, ENode e) {
        this.line = line;
        this.e1 = e;
        this.type = COMP_EXP;
    }

    public CompoundExpression(int line, ENode e1, RelOp rel, ENode e2) {
        this(line, e1);
        this.rel = rel;
        this.e2 = e2;
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
