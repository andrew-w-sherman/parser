package bpl.nodes;
import bpl.Token;
import bpl.exceptions.*;
public class TNode extends ExpressionNode {

    public TNode t;
    public MulOp mul;
    public FNode f;

    public TNode(int line) {
        this.line = line;
        this.type = T;
    }

    public TNode(int line, FNode f) {
        this(line);
        this.f = f;
    }

    public TNode(int line, TNode t, MulOp mul, FNode f) {
        this(line);
        this.t = t;
        this.mul = mul;
        this.f = f;
    }

    public boolean isLNode() {
        if (mul == null && f.isLNode()) return true;
        return false;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("T\n");
        if (f != null && mul != null) {
            t.printRec(depth+1);
            mul.printRec(depth+1);
        }
        f.printRec(depth+1);
        if (next != null) next.printRec(depth);
    }
}
