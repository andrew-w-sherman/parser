public class TNode extends ExpressionNode {

    Factor fact;

    private TNode(int line) {
        this.line = line;
        this.type = T;
    }

    public TNode(int line, Factor fact) {
        this(line);
        this.fact = fact;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("T\n");
        if (fact != null) fact.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
