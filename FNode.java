public class FNode extends ExpressionNode {

    Token ptrOp;
    Factor fact;
    boolean negation;
    FNode fn;

    private FNode(int line) {
        this.line = line;
        this.type = F;
        this.negation = false;
    }

    public FNode(int line, Factor fact) {
        this(line);
        this.fact = fact;
    }

    public FNode(int line, Token ptrOp, Factor fact) {
        this(line);
        this.ptrOp = ptrOp;
        this.fact = fact;
    }

    public FNode(int line, FNode fn) {
        this(line);
        this.negation = true;
        this.fn = fn;
    }

    public boolean isLNode() {
        if (!negation && ptrOp == null && fact.isLNode()) return true;
        return false;
    }

    public void printRec(int depth) {
        printDepth(depth);
        if (negation) System.out.print("-");
        if (ptrOp != null) {
            System.out.print(ptrOp.value);
        }
        System.out.print("F\n");
        if (fact != null) fact.printRec(depth + 1);
        if (fn != null) fn.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
