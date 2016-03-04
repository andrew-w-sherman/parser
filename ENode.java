public class ENode extends ExpressionNode {

    public Factor fact;

    public ENode(int line, Factor fact) {
        this.fact = fact;
        this.line = line;
        this.type = RELOP;
    }

    public void printRec(int depth) {
        printDepth(depth);
        System.out.println("E");
        fact.printRec(depth+1);
        if (next != null) next.printRec(depth);
    }
}
