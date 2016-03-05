public class FNode extends ExpressionNode {

    Factor fact;

    private FNode(int line) {
        this.line = line;
        this.type = F;
    }

    public FNode(int line, Factor fact) {
        this(line);
        this.fact = fact;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.print("F\n");
        if (fact != null) fact.printRec(depth + 1);
        if (next != null) next.printRec(depth);
    }
}
